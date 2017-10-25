package com.ikechukwuakalu.krypto.data.remote;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ConverterService {
    @GET("data/price?")
    Flowable<ResponseBody> getRate(@Query("fsym") String cryptoCode,
                                   @Query("tsyms") String currencyCodes);
}