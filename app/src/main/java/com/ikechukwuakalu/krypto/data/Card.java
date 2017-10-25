package com.ikechukwuakalu.krypto.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Card {

    private long id;

    @NonNull
    private String cryptoCode;

    @NonNull
    private String currencyCode;

    @Nullable
    private String value;

    public Card(long id, @NonNull String cryptoCode, @NonNull String currencyCode) {
        this(id, cryptoCode, currencyCode, null);
    }

    public Card(long id, @NonNull String cryptoCode, @NonNull String currencyCode, @Nullable String value){
        this.id = id;
        this.cryptoCode = cryptoCode;
        this.currencyCode = currencyCode;
        this.value = value;
    }

    public void setValue(@Nullable String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getCryptoCode() {
        return cryptoCode;
    }

    @NonNull
    public String getCurrencyCode() {
        return currencyCode;
    }

    @Nullable
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return !(obj == null || obj.getClass() != this.getClass());
    }

    @Override
    public String toString() {
        return "Card (id = " + id +
                " cryptoCode = " + cryptoCode +
                ", currencyCode = " + currencyCode +
                ", value = " + value +
                ")";
    }
}
