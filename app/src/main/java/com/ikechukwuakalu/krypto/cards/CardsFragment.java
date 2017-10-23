package com.ikechukwuakalu.krypto.cards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikechukwuakalu.krypto.BaseFragment;
import com.ikechukwuakalu.krypto.R;
import com.ikechukwuakalu.krypto.createcard.CreateCardActivity;
import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.utils.AppUtils;

import java.util.List;

public class CardsFragment extends BaseFragment implements CardsContract.View {

    CardsContract.Presenter presenter;

    FloatingActionButton fab;

    public CardsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cards_frag, container, false);
        fab = v.findViewById(R.id.createNewCardFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateCardsView();
            }
        });
        return v;
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
    public void setPresenter(CardsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void openCreateCardsView() {
        Intent intent = new Intent(getActivity(), CreateCardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openConverterView(Card card) {

    }

    @Override
    public void showCards(List<Card> cards) {

    }

    @Override
    public void showErrorLoadingCards(String msg) {
        AppUtils.showToast(getContext(), msg);
    }

    @Override
    public void showNoCardsFound() {
        AppUtils.showToast(getContext(), "No cards available");
    }

    @Override
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }
}
