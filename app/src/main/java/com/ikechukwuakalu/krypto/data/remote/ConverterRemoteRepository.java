package com.ikechukwuakalu.krypto.data.remote;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.ConverterDataSource;
import com.ikechukwuakalu.krypto.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

public class ConverterRemoteRepository implements ConverterDataSource{

    @Override
    public Flowable<ResponseBody> loadConversionRate(Card card) {
        ConverterService service = AppUtils.createService(ConverterService.class);
        return service.getRate(card.getCryptoCode(), card.getCurrencyCode());
    }

    @Override
    public String getCurrencyValue(String response, String currencyCode) {
        String value;
        try {
            JSONObject obj = new JSONObject(response);
            value = obj.getString(currencyCode);
        } catch (JSONException e) {
            value = "0";
        }
        return value;
    }
}
