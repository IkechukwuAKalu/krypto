package com.ikechukwuakalu.krypto.converter;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.mvp.BasePresenter;
import com.ikechukwuakalu.krypto.mvp.BaseView;

interface ConverterContract {

    interface View extends BaseView {

        void showLoading();

        void hideLoading();

        void setTitle(String title);

        void showCurrencyValue(String value);

        void showCryptoValue(String value);

        void showConversionRate(Card card);

        void showError(String msg);

        void enableCrypto();

        void disableCrypto();

        void enableCurrency();

        void disableCurrency();

        void clearCryptoValue();

        void clearCurrencyValue();
    }

    interface Presenter extends BasePresenter<View> {

        void loadConversionRate();

        void performConversion(String crypto, String currency);
    }
}
