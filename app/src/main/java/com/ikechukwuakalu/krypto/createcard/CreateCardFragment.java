package com.ikechukwuakalu.krypto.createcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikechukwuakalu.krypto.BaseFragment;
import com.ikechukwuakalu.krypto.R;
import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.Currency;
import com.ikechukwuakalu.krypto.utils.AppUtils;

import java.util.List;

public class CreateCardFragment extends BaseFragment implements CreateCardContract.View {

    private CreateCardContract.Presenter presenter;

    AppCompatButton createCardBtn;

    public CreateCardFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_card_frag, container, false);
        createCardBtn = v.findViewById(R.id.createCardBtn);
        createCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCard();
            }
        });
        return v;
    }

    private void createCard() {
        presenter.saveCard(new Card());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stop();
    }

    @Override
    public void setPresenter(CreateCardContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showCurrencies(List<Currency> currencies) {
        AppUtils.showToast(getContext(), "Currencies loaded");
    }

    @Override
    public void showSaveSuccess() {
        AppUtils.showToast(getContext(), "Card created successfully");
    }

    @Override
    public void showSaveError(String msg) {
        AppUtils.showToast(getContext(), msg);
    }
}