package com.ikechukwuakalu.krypto.data;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

public interface ConverterDataSource {

    Flowable<ResponseBody> loadConversionRate(Card card);

    String getCurrencyValue(String response, String currencyCode);
}
