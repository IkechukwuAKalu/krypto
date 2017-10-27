package com.ikechukwuakalu.krypto.utils.rx;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * This class will be used for unit tests
 */
public class ImmediateScheduler implements BaseScheduler{

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }
}
