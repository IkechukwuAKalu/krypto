package com.ikechukwuakalu.krypto.utils.espresso;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

public class SimpleCountingResource implements IdlingResource{

    private String resourceName;

    private AtomicInteger counter = new AtomicInteger(0);

    @Nullable
    private ResourceCallback resourceCallback;

    SimpleCountingResource(String resource) {
        this.resourceName = resource;
    }

    @Override
    public String getName() {
        return resourceName;
    }

    @Override
    public boolean isIdleNow() {
        return counter.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    public void increment() {
        counter.getAndIncrement();
    }

    public void decrement() {
        int current = counter.decrementAndGet();

        if (current == 0) {
            if (resourceCallback != null)
                resourceCallback.onTransitionToIdle();
        } else if(current < 0) {
            throw new IllegalArgumentException("Counter is corrupt");
        }
    }
}
