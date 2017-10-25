package com.ikechukwuakalu.krypto.utils.rx;

import io.reactivex.Scheduler;

public interface BaseScheduler {

    Scheduler io();

    Scheduler ui();

    Scheduler computation();
}
