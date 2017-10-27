package com.ikechukwuakalu.krypto.data;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

public class ConverterRepository implements ConverterDataSource {

    private ConverterDataSource converterRemoteRepository;

    public ConverterRepository(ConverterDataSource converterRemoteRepository) {
        this.converterRemoteRepository = converterRemoteRepository;
    }

    @Override
    public Flowable<ResponseBody> loadConversionRate(Card card) {
        return converterRemoteRepository.loadConversionRate(card);
    }

    @Override
    public String getCurrencyValue(String response, String currencyCode) {
        return converterRemoteRepository.getCurrencyValue(response, currencyCode);
    }
}
