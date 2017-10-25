package com.ikechukwuakalu.krypto.utils.espresso;

import android.support.test.espresso.IdlingResource;

public class EspressoIdlingResource {

    private static SimpleCountingResource countingResource = new SimpleCountingResource("KRYPTO_RESOURCE");

    public static void increment() {
        countingResource.increment();
    }

    public static void decrement() {
        countingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return countingResource;
    }
}
