package com.ikechukwuakalu.krypto.cards;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ikechukwuakalu.krypto.BaseActivity;
import com.ikechukwuakalu.krypto.R;

public class CardsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards_act);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardsFragment fragment = new CardsFragment();
        new CardsPresenter(fragment);
        addFragment(R.id.cardsActContainer, fragment);
    }
}
