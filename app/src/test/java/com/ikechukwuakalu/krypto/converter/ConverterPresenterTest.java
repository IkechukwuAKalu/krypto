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

    private final String CARD_ID = "32";
    @Mock
    private ConverterContract.View view;
    private CardsRepository cardsRepository;
    private ConverterPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        FakeCardsRepository fakeCardsRepository = new FakeCardsRepository();
        cardsRepository = new CardsRepository(fakeCardsRepository);

        ConverterRepository converterRepository = new ConverterRepository(new FakeConverterRepository());
        presenter = new ConverterPresenter(CARD_ID, cardsRepository, converterRepository, new ImmediateScheduler());
        presenter.attachView(view);
    }

    @Test
    public void loadConversionRate_ShowInUI() {
        // Given an initialized presenter and a saved card
        Card card = createAndGetCard(Long.valueOf(CARD_ID));
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
        createAndGetCard(Long.valueOf(CARD_ID));
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
        Card card = createAndGetCard(Long.valueOf(CARD_ID));
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
        Card card = createAndGetCard(Long.valueOf(CARD_ID));
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
        createAndGetCard(Long.valueOf(CARD_ID));
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
    private Card createAndGetCard(long cardId) {
        String CRYPTO_CODE = "ETH";
        String CURRENCY_CODE = "EUR";
        Card card = new Card(cardId, CRYPTO_CODE, CURRENCY_CODE);
        cardsRepository.saveCard(card);
        return card;
    }
}