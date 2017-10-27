package com.ikechukwuakalu.krypto.converter;

import com.ikechukwuakalu.krypto.FakeCardsRepository;
import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.CardsRepository;
import com.ikechukwuakalu.krypto.data.ConverterRepository;
import com.ikechukwuakalu.krypto.utils.rx.ImmediateScheduler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class ConverterPresenterTest {

    @Mock
    private ConverterContract.View view;

    private CardsRepository cardsRepository;

    private ConverterPresenter presenter;

    private final String CRYPTO_CODE = "ETH";
    private final String CURRENCY_CODE = "EUR";
    private Card card = new Card(0, CRYPTO_CODE, CURRENCY_CODE);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        FakeCardsRepository fakeCardsRepository = new FakeCardsRepository();
        cardsRepository = new CardsRepository(fakeCardsRepository);

        String CARD_ID = "32";
        ConverterRepository converterRepository = new ConverterRepository(new FakeConverterRepository());
        presenter = new ConverterPresenter(CARD_ID, cardsRepository, converterRepository, new ImmediateScheduler());
        presenter.attachView(view);
    }

    @Test
    public void loadConversionRate_ShowInUI() {
        // Given an initialized presenter and a saved card
        createNewCard();
        // When conversion rate is loaded
        presenter.loadConversionRate();
        verify(view).showLoading();
        // Verify rates shown
        verify(view).hideLoading();
        verify(view).showConversionRate(card);
    }

    @Test
    public void performConversionWithNoFieldSet() {
        // Given an initialized presenter, card, crypto value and a currency value
        String cryptoValue = "";
        String currencyValue = "";
        createNewCard();
        // When unsupported conversion is made with no conversion value set
        presenter.loadConversionRate();
        presenter.performConversion(cryptoValue, currencyValue);
        // Clear fields and show error
        verify(view).showError("Both fields cannot be empty");
    }

    @Test
    public void convertCryptoToCurrency() {
        // Given an initialized presenter, card and a crypto value
        String cryptoValue = "1200";
        createNewCard();
        // When conversion is performed
        presenter.loadConversionRate();
        presenter.performConversion(cryptoValue, "");
        // Verify currency value is shown and currency is enabled
        Double value = Double.valueOf(card.getValue()) * Double.valueOf(cryptoValue);
        verify(view).showCurrencyValue(String.valueOf(value));
        verify(view).enableCurrency();
    }

    @Test
    public void convertCurrencyToCrypto() {
        // Given an initialized presenter, card and a currency value
        String currencyValue = "2400";
        createNewCard();
        // When conversion is performed
        presenter.loadConversionRate();
        presenter.performConversion("", currencyValue);
        // Verify crypto value is shown and crypto is enabled
        Double value = Double.valueOf(currencyValue) / Double.valueOf(card.getValue());
        verify(view).showCryptoValue(String.valueOf(value));
        verify(view).enableCrypto();
    }

    @Test
    public void performConversionWithCryptoAndCurrency() {
        // Given an initialized presenter, card, crypto value and a currency value
        String cryptoValue = "1200";
        String currencyValue = "2400";
        createNewCard();
        // When unsupported conversion is made with two fields
        presenter.loadConversionRate();
        presenter.performConversion(cryptoValue, currencyValue);
        // Clear fields and show error
        verify(view).clearCryptoValue();
        verify(view).clearCurrencyValue();
        verify(view).showError("Only one field is required");
    }

    /**
     * Populates the repository with a card
     */
    private void createNewCard() {
        cardsRepository.saveCard(card);
    }
}