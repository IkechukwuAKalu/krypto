package com.ikechukwuakalu.krypto.createcard;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ikechukwuakalu.krypto.BaseActivity;
import com.ikechukwuakalu.krypto.R;
import com.ikechukwuakalu.krypto.data.local.CardsRepository;

public class CreateCardActivity extends BaseActivity {

    CreateCardPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_act);
        setupToolbar(R.id.toolbar);

        CreateCardFragment fragment = new CreateCardFragment();
        CardsRepository cardsRepository = new CardsRepository(this);
        presenter = new CreateCardPresenter(cardsRepository);

        addFragment(R.id.defaultFragContainer, fragment, "create");
    }
}
