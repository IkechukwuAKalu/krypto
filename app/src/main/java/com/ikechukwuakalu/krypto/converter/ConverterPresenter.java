package com.ikechukwuakalu.krypto.converter;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.CardsDataSource;
import com.ikechukwuakalu.krypto.data.CardsRepository;
import com.ikechukwuakalu.krypto.data.ConverterRepository;
import com.ikechukwuakalu.krypto.utils.espresso.EspressoIdlingResource;
import com.ikechukwuakalu.krypto.utils.rx.BaseScheduler;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

class ConverterPresenter implements ConverterContract.Presenter{

    private ConverterContract.View view = null;

    private String cardId;

    private BaseScheduler rxScheduler;

    private CardsRepository cardsRepository;

    private ConverterRepository converterRepository;

    private Card card = null;

    private CompositeDisposable disposables = new CompositeDisposable();

    ConverterPresenter(String cardId, CardsDataSource cardsRepository, ConverterRepository converterRepository,
                       BaseScheduler rxScheduler) {
        this.cardId = cardId;
        this.cardsRepository = (CardsRepository) cardsRepository;
        this.converterRepository = converterRepository;
        this.rxScheduler = rxScheduler;
    }

    @Override
    public void attachView(ConverterContract.View view) {
        this.view = view;
        if (view != null) {
            view.setTitle("Krypto Converter");
        }
    }

    @Override
    public void detachView() {
        disposables.clear();
        view = null;
    }

    @Override
    public void loadConversionRate() {
        if (view != null) {
            view.showLoading();
        }
        EspressoIdlingResource.increment();
        Disposable disposable = cardsRepository.loadCard(cardId)
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .subscribe(new Consumer<Card>() {
                    @Override
                    public void accept(Card card) throws Exception {
                        setCardValue(card);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (view != null) {
                            view.showError("Error loading card");
                        }
                    }
                });
        disposables.clear();
        disposables.add(disposable);
    }

    private void setCardValue(Card card) {
        ConverterPresenter.this.card = card;
        Disposable disposable = converterRepository.loadConversionRate(card)
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        // continuation from the `loadConversionRate` method
                        if (view != null) {
                            view.hideLoading();
                        }
                        EspressoIdlingResource.decrement();
                    }
                })
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String value = converterRepository.getCurrencyValue(responseBody.string(),
                                ConverterPresenter.this.card.getCurrencyCode());
                        // Keep reference to value
                        ConverterPresenter.this.card.setValue(value);
                        if (view != null) {
                            view.showConversionRate(ConverterPresenter.this.card);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (view != null) {
                            view.showError("Unable to connect to the internet.");
                        }
                    }
                });
        disposables.clear();
        disposables.add(disposable);
    }

    @Override
    public void performConversion(String crypto, String currency) {
        if (card.getValue() == null) {
            if (view != null) {
                view.showError("Unable to perform conversion");
            }
            return;
        }
        if (crypto.isEmpty() && currency.isEmpty()) {
            if (view != null) {
                view.showError("Both fields cannot be empty");
            }
            return;
        }
        if (!crypto.isEmpty() && currency.isEmpty())
            convertToCurrency(crypto);
        else if (crypto.isEmpty() && !currency.isEmpty())
            convertToCrypto(currency);
        else {
            if (view != null) {
                view.clearCryptoValue();
                view.clearCurrencyValue();
                view.showError("Only one field is required");
            }
        }
    }

    private void convertToCurrency(String crypto) {
        Double result = Double.valueOf(card.getValue()) * Double.valueOf(crypto);
        if (view != null) {
            view.showCurrencyValue(String.valueOf(result));
            view.enableCurrency();
        }
    }

    private void convertToCrypto(String currency) {
        Double result = Double.valueOf(currency) / Double.valueOf(card.getValue());
        if (view != null) {
            view.showCryptoValue(String.valueOf(result));
            view.enableCrypto();
        }
    }
}