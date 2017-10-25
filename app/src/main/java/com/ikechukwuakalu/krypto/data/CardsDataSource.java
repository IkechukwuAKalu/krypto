package com.ikechukwuakalu.krypto.data;

import java.util.List;

import io.reactivex.Flowable;

public interface CardsDataSource {

    boolean saveCard(Card card);

    Flowable<List<Card>> loadCards();

    Flowable<Card> loadCard(String cardId);

    void deleteCard(Card card);

    void deleteAllCards();
}
