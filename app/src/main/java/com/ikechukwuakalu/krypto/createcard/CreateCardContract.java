package com.ikechukwuakalu.krypto.createcard;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.Currency;
import com.ikechukwuakalu.krypto.mvp.BasePresenter;
import com.ikechukwuakalu.krypto.mvp.BaseView;

import java.util.List;

public interface CreateCardContract {

    interface View extends BaseView<Presenter> {

        void showCurrencies(List<Currency> currencies);

        void showSaveSuccess();

        void showSaveError(String msg);
    }

    interface Presenter extends BasePresenter {

        void loadCurrencies();

        void saveCard(Card card);
    }
}
