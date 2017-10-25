package com.ikechukwuakalu.krypto.mvp;

public interface BasePresenter<T> {

    void attachView(T view);

    void detachView();
}
