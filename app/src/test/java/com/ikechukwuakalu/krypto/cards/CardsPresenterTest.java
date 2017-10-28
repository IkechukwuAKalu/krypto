package com.ikechukwuakalu.krypto.cards;

import com.ikechukwuakalu.krypto.FakeCardsRepository;
import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.CardsRepository;
import com.ikechukwuakalu.krypto.utils.rx.BaseScheduler;
import com.ikechukwuakalu.krypto.utils.rx.ImmediateScheduler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

public class CardsPresenterTest {

    @Mock
    private CardsContract.View view;

    private FakeCardsRepository fakeCardsRepository;
    private CardsRepository cardsRepository;

    private CardsPresenter presenter;

    private Card card = new Card(0, "BTC", "NGR", "1230000");

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        fakeCardsRepository = new FakeCardsRepository();
        cardsRepository = new CardsRepository(fakeCardsRepository);

        BaseScheduler scheduler = new ImmediateScheduler();
        presenter = new CardsPresenter(cardsRepository, scheduler);
        presenter.attachView(view);
    }

    @Test
    public void loadCards_CheckItemsShown(){
        // Given an attached presenter and a non-empty list
        createNewCard();
        // When cards are loaded
        presenter.loadCards();
        verify(view).showLoading();
        // Verify cards are shown
        verify(view).hideLoading();
        List<Card> cards = new ArrayList<>();
        cards.addAll(fakeCardsRepository.cards.values());
        verify(view).showCards(cards);
    }

    @Test
    public void deleteSingleCard_CheckSuccessMsgShown() {
        // Given an attached presenter and a card
        createNewCard();
        // When a card is deleted
        presenter.deleteCard(card);
        // Verify message shown
        verify(view).showDeleteSuccess();
    }

    @Test
    public void deleteAllCards_CheckSuccessMsgShown() {
        // Given an attached presenter and somw Cards
        createNewCard();
        createNewCard();
        // When a card is deleted
        presenter.deleteAllCards();
        // Verify message shown
        verify(view).showDeleteSuccess();
    }

    @Test
    public void loadEmptyCards_CheckNoItemShown() {
        // Given an attached presenter and an empty list
        createNewCard();
        createNewCard();
        // When cards are loaded
        cardsRepository.deleteAllCards();
        presenter.loadCards();
        verify(view).showLoading();
        // Verify cards are shown
        verify(view).hideLoading();
        verify(view).showNoCardsFound();
    }

    /**
     *  Populates the Cards repository with data
     */
    private void createNewCard() {
        cardsRepository.saveCard(card);
    }
}