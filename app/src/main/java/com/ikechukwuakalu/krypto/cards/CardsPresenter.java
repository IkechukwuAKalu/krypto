package com.ikechukwuakalu.krypto.cards;

import com.ikechukwuakalu.krypto.data.Card;

class CardsPresenter implements CardsContract.Presenter {

    private CardsContract.View view;

    CardsPresenter(CardsContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        loadCards();
        view.setTitle("Krypto Cards");
    }

    @Override
    public void stop() {

    }

    @Override
    public void loadCards() {
        view.showNoCardsFound();
    }

    @Override
    public void deleteCard(Card card) {

    }
}
