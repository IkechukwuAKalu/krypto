package com.ikechukwuakalu.krypto;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.CardsDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * This class will be used for testing
 */
public class FakeCardsRepository implements CardsDataSource{

    public List<Card> cards = new ArrayList<>();

    @Override
    public boolean saveCard(Card card) {
        cards.add(card);
        return card.getId() >= 0; // negative values will trigger save error
    }

    @Override
    public Flowable<List<Card>> loadCards() {
        return Flowable.just(cards);
    }

    @Override
    public Flowable<Card> loadCard(String cardId) {
        int lastIndex = cards.size() - 1;
        return Flowable.just(cards.get(lastIndex));
    }

    @Override
    public void deleteCard(Card card) {
        int lastIndex = cards.size() - 1;
        cards.remove(lastIndex);
    }

    @Override
    public void deleteAllCards() {
        cards.clear();
    }
}