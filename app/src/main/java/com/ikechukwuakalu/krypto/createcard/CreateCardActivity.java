package com.ikechukwuakalu.krypto.createcard;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ikechukwuakalu.krypto.BaseActivity;
import com.ikechukwuakalu.krypto.R;

public class CreateCardActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_card_act);
        setupToolbar(R.id.toolbar);

        CreateCardFragment fragment = new CreateCardFragment();
        new CreateCardPresenter(fragment);
        addFragment(R.id.createCardContainer, fragment);
    }
}
