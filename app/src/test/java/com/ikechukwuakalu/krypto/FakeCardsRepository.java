package com.ikechukwuakalu.krypto;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.CardsDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

/**
 * This class will be used for testing
 */
public class FakeCardsRepository implements CardsDataSource{
    public Map<String, Card> cards = new HashMap<>();

    public FakeCardsRepository() {
        List<Card> cardss = new ArrayList<>();
        cardss.addAll(cards.values());
        for (int i = 0; i < cardss.size(); i++) {
            System.out.println(cardss.get(i));
        }
    }

    @Override
    public boolean saveCard(Card card) {
        cards.put(String.valueOf(card.getId()), card);
        return card.getId() >= 0; // negative values will trigger save error
    }

    @Override
    public Flowable<List<Card>> loadCards() {
        List<Card> cardItems = new ArrayList<>();
        cardItems.addAll(cards.values());
        return Flowable.just(cardItems);
    }

    @Override
    public Flowable<Card> loadCard(String cardId) {
        return Flowable.just(cards.get(cardId));
    }

    @Override
    public void deleteCard(Card card) {
        cards.remove(String.valueOf(card.getId()));
    }

    @Override
    public void deleteAllCards() {
        cards.clear();
    }
}