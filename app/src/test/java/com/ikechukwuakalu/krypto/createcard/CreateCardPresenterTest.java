package com.ikechukwuakalu.krypto.createcard;

import com.ikechukwuakalu.krypto.FakeCardsRepository;
import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.CardsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateCardPresenterTest {

    @Mock
    private CreateCardContract.View view;

    private CreateCardPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        CardsRepository repository = new CardsRepository(new FakeCardsRepository());

        presenter = new CreateCardPresenter(repository);
        presenter.attachView(view);
    }

    @Test
    public void checkSpinnerItemsLoaded() {
        // Given an initialized presenter

        // When view is loaded
        presenter.initViews();
        // Verify spinner data are loaded
        // The methods are invoked twice where the first is when the presenter is attached to the view
        // while the second is when it is forced in the test via `presenter.initViews()`
        verify(view, times(2)).showCryptos();
        verify(view, times(2)).showCurrencies();
    }

    @Test
    public void clickOnButton_CheckSaveSuccess() {
        // Given an initialized presenter and a card
        Card card = new Card(0, "BTC", "NGR", "1234000");
        // When `create` button is clicked
        presenter.saveCard(card);
        // Verify success is shown
        verify(view).showSaveSuccess();
        verify(view).showCardsView();
    }

    @Test
    public void clickOnButton_CheckSaveError() {
        // Given an initialized presenter and a card
        Card card = new Card(-1, "BTC", "NGR", "1234000");
        // When `create` button is clicked
        presenter.saveCard(card);
        // Verify success is shown
        verify(view).showSaveError("Card already exists");
    }
}
