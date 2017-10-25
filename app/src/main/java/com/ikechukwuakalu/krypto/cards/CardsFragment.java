package com.ikechukwuakalu.krypto.cards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ikechukwuakalu.krypto.BaseFragment;
import com.ikechukwuakalu.krypto.R;
import com.ikechukwuakalu.krypto.converter.ConverterActivity;
import com.ikechukwuakalu.krypto.createcard.CreateCardActivity;
import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.utils.AppUtils;

import java.util.List;

public class CardsFragment extends BaseFragment implements CardsContract.View {

    private CardsPresenter presenter;

    FloatingActionButton createCardFab;
    ProgressBar progressBar;
    RecyclerView cardsRv;

    public CardsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = ((CardsActivity) getActivity()).presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cards_frag, container, false);
        createCardFab = v.findViewById(R.id.createNewCardFab);
        createCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateCardsView();
            }
        });
        progressBar = v.findViewById(R.id.cardsProgressBar);
        cardsRv = v.findViewById(R.id.cardsRv);

        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cards_frag_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteAllCards) {
            presenter.deleteAllCards();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        cardsRv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        cardsRv.setVisibility(View.VISIBLE);
    }

    @Override
    public void openCreateCardsView() {
        Intent intent = new Intent(getActivity(), CreateCardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openConverterView(Card card) {
        Intent intent = new Intent(getActivity(), ConverterActivity.class);
        intent.putExtra(ConverterActivity.CARD_KEY, String.valueOf(card.getId()));
        startActivity(intent);
    }

    @Override
    public void showCards(List<Card> cards) {
        CardsAdapter adapter = new CardsAdapter(cards, cardClickListener(), cardOptionsClickListener());
        adapter.notifyDataSetChanged();
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        cardsRv.setVisibility(View.VISIBLE);
        cardsRv.setLayoutManager(glm);
        cardsRv.setAdapter(adapter);
    }

    private View.OnClickListener cardClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = (Card) v.getTag();
                openConverterView(card);
            }
        };
    }

    private View.OnClickListener cardOptionsClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Card card = (Card) v.getTag();
                PopupMenu menu = new PopupMenu(getContext(), v, Gravity.END);
                menu.inflate(R.menu.card_options);
                handleCardMenuItemSelected(menu, card);
                menu.show();
            }
        };
    }

    private void handleCardMenuItemSelected(PopupMenu menu, final Card card) {
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.deleteCard) {
                    presenter.deleteCard(card);
                }
                return true;
            }
        });
    }

    @Override
    public void showErrorLoadingCards(String msg) {
        AppUtils.showLongToast(getContext(), msg);
    }

    @Override
    public void showNoCardsFound() {
        AppUtils.showLongToast(getContext(), "No Card available.");
    }

    @Override
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    @Override
    public void showDeleteSuccess() {
        AppUtils.showShortSnackbar(cardsRv, "Delete was successful");
    }
}
