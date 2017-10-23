package com.ikechukwuakalu.krypto.createcard;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.Currency;

import java.util.Collections;

class CreateCardPresenter implements CreateCardContract.Presenter {

    private CreateCardContract.View view;

    CreateCardPresenter(CreateCardContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        loadCurrencies();
    }

    @Override
    public void stop() {

    }

    @Override
    public void loadCurrencies() {
        view.showCurrencies(Collections.<Currency>emptyList());
    }

    @Override
    public void saveCard(Card card) {

    }
}
