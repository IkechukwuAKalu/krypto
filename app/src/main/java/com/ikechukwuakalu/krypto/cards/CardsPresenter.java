package com.ikechukwuakalu.krypto.cards;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.CardsDataSource;
import com.ikechukwuakalu.krypto.data.CardsRepository;
import com.ikechukwuakalu.krypto.utils.espresso.EspressoIdlingResource;
import com.ikechukwuakalu.krypto.utils.rx.BaseScheduler;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

class CardsPresenter implements CardsContract.Presenter {

    private CardsContract.View view = null;

    private CardsRepository cardsRepository;

    private BaseScheduler rxScheduler;

    private CompositeDisposable disposables = new CompositeDisposable();

    CardsPresenter(CardsDataSource cardsRepository, BaseScheduler rxScheduler) {
        this.cardsRepository = (CardsRepository) cardsRepository;
        this.rxScheduler = rxScheduler;
    }

    @Override
    public void attachView(CardsContract.View view) {
        this.view = view;
        if (view != null) {
            view.setTitle("Krypto Cards");
        }
    }

    @Override
    public void detachView() {
        disposables.clear();
        view = null;
    }

    @Override
    public void loadCards() {
        if (view != null) {
            view.showLoading();
        }
        EspressoIdlingResource.increment();
        Disposable disposable = cardsRepository.loadCards()
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (view != null) {
                            view.hideLoading();
                        }
                        EspressoIdlingResource.decrement();
                    }
                })
                .subscribe(new Consumer<List<Card>>() {
                    @Override
                    public void accept(List<Card> cards) throws Exception {
                        if (view != null) {
                            if (cards.size() < 1) {
                                view.showNoCardsFound();
                            } else {
                                view.showCards(cards);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                       if (view != null) {
                           view.showErrorLoadingCards(throwable.getMessage());
                       }
                    }
                });
        disposables.clear();
        disposables.add(disposable);
    }

    @Override
    public void deleteCard(Card card) {
        cardsRepository.deleteCard(card);
        if (view != null) {
            view.showDeleteSuccess();
        }
        // to refresh adapter
        loadCards();
    }

    @Override
    public void deleteAllCards() {
        cardsRepository.deleteAllCards();
        if (view != null) {
            view.showDeleteSuccess();
        }
        // to refresh adapter
        loadCards();
    }
}
