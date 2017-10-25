package com.ikechukwuakalu.krypto.createcard;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.mvp.BasePresenter;
import com.ikechukwuakalu.krypto.mvp.BaseView;

interface CreateCardContract {

    interface View extends BaseView {

        void setTitle(String title);

        void showCryptos();

        void showCurrencies();

        void showSaveSuccess();

        void showSaveError(String msg);

        void showCardsView();
    }

    interface Presenter extends BasePresenter<View> {

        void initViews();

        void saveCard(Card card);
    }
}
