package com.ikechukwuakalu.krypto.createcard;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ikechukwuakalu.krypto.BaseActivity;
import com.ikechukwuakalu.krypto.R;
import com.ikechukwuakalu.krypto.data.CardsRepository;
import com.ikechukwuakalu.krypto.data.local.CardsLocalRepository;

public class CreateCardActivity extends BaseActivity {

    CreateCardPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_act);
        setupToolbar(R.id.toolbar);

        CreateCardFragment fragment = new CreateCardFragment();
        CardsLocalRepository localRepository = new CardsLocalRepository(this);
        CardsRepository cardsRepository = new CardsRepository(localRepository);
        presenter = new CreateCardPresenter(cardsRepository);

        addFragment(R.id.defaultFragContainer, fragment, "create");
    }
}
