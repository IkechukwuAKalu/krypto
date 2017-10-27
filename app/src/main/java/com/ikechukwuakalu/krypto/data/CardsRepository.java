package com.ikechukwuakalu.krypto.data;

import java.util.List;

import io.reactivex.Flowable;

public class CardsRepository implements CardsDataSource{

    private CardsDataSource localRepository;

    public CardsRepository(CardsDataSource localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public boolean saveCard(Card card) {
        return localRepository.saveCard(card);
    }

    @Override
    public Flowable<List<Card>> loadCards() {
        return localRepository.loadCards();
    }

    @Override
    public Flowable<Card> loadCard(String cardId) {
        return localRepository.loadCard(cardId);
    }

    @Override
    public void deleteCard(Card card) {
        localRepository.deleteCard(card);
    }

    @Override
    public void deleteAllCards() {
        localRepository.deleteAllCards();
    }
}
