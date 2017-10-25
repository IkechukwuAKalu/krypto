package com.ikechukwuakalu.krypto.converter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ikechukwuakalu.krypto.BaseActivity;
import com.ikechukwuakalu.krypto.R;
import com.ikechukwuakalu.krypto.data.local.CardsRepository;
import com.ikechukwuakalu.krypto.utils.rx.RxScheduler;

public class ConverterActivity extends BaseActivity{

    public static final String CARD_KEY = "card_key";

    ConverterPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_act);
        setupToolbar(R.id.toolbar);

        String cardId = getIntent().getExtras().getString(CARD_KEY);
        ConverterFragment fragment = new ConverterFragment();
        CardsRepository cardsRepository = new CardsRepository(this);
        RxScheduler rxScheduler = new RxScheduler();
        presenter = new ConverterPresenter(cardId, cardsRepository, rxScheduler);

        addFragment(R.id.defaultFragContainer, fragment, "converter");
    }
}