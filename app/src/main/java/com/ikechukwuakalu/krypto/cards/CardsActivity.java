package com.ikechukwuakalu.krypto.cards;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ikechukwuakalu.krypto.BaseActivity;
import com.ikechukwuakalu.krypto.R;
import com.ikechukwuakalu.krypto.data.CardsRepository;
import com.ikechukwuakalu.krypto.data.local.CardsLocalRepository;
import com.ikechukwuakalu.krypto.utils.rx.RxScheduler;

public class CardsActivity extends BaseActivity {

    CardsFragment fragment = null;
    CardsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_act);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (fragment == null)
            fragment = new CardsFragment();

        CardsLocalRepository localRepository = new CardsLocalRepository(this);
        CardsRepository cardsRepository = new CardsRepository(localRepository);
        RxScheduler rxScheduler = new RxScheduler();
        presenter = new CardsPresenter(cardsRepository, rxScheduler);

        addFragment(R.id.defaultFragContainer, fragment, "cards");
    }
}
