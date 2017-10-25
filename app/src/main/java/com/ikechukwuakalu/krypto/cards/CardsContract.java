package com.ikechukwuakalu.krypto.cards;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.mvp.BasePresenter;
import com.ikechukwuakalu.krypto.mvp.BaseView;

import java.util.List;

interface CardsContract {

    interface View extends BaseView {

        void showLoading();

        void hideLoading();

        void openCreateCardsView();

        void openConverterView(Card card);

        void showCards(List<Card> cards);

        void showErrorLoadingCards(String msg);

        void showNoCardsFound();

        void setTitle(String title);

        void showDeleteSuccess();
    }

    interface Presenter extends BasePresenter<View> {

        void loadCards();

        void deleteCard(Card card);

        void deleteAllCards();
    }
}
