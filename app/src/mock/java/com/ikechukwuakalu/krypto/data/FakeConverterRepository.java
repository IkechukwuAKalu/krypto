package com.ikechukwuakalu.krypto.data;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;

public class FakeConverterRepository implements ConverterDataSource {

    @Override
    public Flowable<ResponseBody> loadConversionRate(Card card) {
        String JSON_RES = "{\"EUR\": 324}";
        ResponseBody responseBody = ResponseBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                JSON_RES
        );
        return Flowable.just(responseBody);
    }

    @Override
    public String getCurrencyValue(String response, String currencyCode) {
        return "324";
    }
}
