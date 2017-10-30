package com.ikechukwuakalu.krypto.createcard;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.CardsDataSource;
import com.ikechukwuakalu.krypto.data.CardsRepository;

class CreateCardPresenter implements CreateCardContract.Presenter {

    private CreateCardContract.View view = null;

    private CardsRepository cardsRepository;

    CreateCardPresenter(CardsDataSource cardsRepository) {
        this.cardsRepository = (CardsRepository) cardsRepository;
    }

    @Override
    public void attachView(CreateCardContract.View view) {
        this.view = view;
        if (view != null) {
            view.setTitle("Create Card");
            initViews();
        }
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void initViews() {
        view.showCryptos();
        view.showCurrencies();
    }

    @Override
    public void saveCard(Card card) {
        if (card.getCryptoCode().equalsIgnoreCase("") || card.getCurrencyCode().equalsIgnoreCase("")) {
            if (view != null) {
                view.showSaveError("Crypto code and currency code must be set");
                return;
            }
        }
        boolean status = cardsRepository.saveCard(card);
        if (status) {
            if (view != null) {
                view.showSaveSuccess();
                view.showCardsView();
            }
        } else {
            if (view != null) {
                view.showSaveError("Card already exists");
            }
        }
    }
}