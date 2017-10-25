package com.ikechukwuakalu.krypto.createcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ikechukwuakalu.krypto.BaseFragment;
import com.ikechukwuakalu.krypto.R;
import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.utils.AppUtils;

public class CreateCardFragment extends BaseFragment implements CreateCardContract.View {

    private CreateCardPresenter presenter;

    AppCompatButton createCardBtn;
    Spinner cryptoSpinner, currencySpinner;

    String cryptoCode = null, currencyCode = null;

    public CreateCardFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = ((CreateCardActivity)getActivity()).presenter;
    }

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
        cryptoSpinner = v.findViewById(R.id.cryptoSpinner);
        currencySpinner = v.findViewById(R.id.currencySpinner);
        return v;
    }

    private void createCard() {
        Card card = new Card(0, cryptoCode, currencyCode);
        presenter.saveCard(card);
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
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    @Override
    public void showCryptos() {
        String[] cryptoNames = getActivity().getResources().getStringArray(R.array.crypto_names);
        String[] cryptoCodes = getActivity().getResources().getStringArray(R.array.crypto_codes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, cryptoNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cryptoSpinner.setAdapter(adapter);
        handleCryptoClick(cryptoCodes);
    }

    private void handleCryptoClick(final String[] cryptoCodes) {
        cryptoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cryptoCode = cryptoCodes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public void showCurrencies() {
        String[] currencyNames = getActivity().getResources().getStringArray(R.array.currency_names);
        String[] currencyCodes = getActivity().getResources().getStringArray(R.array.currency_codes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, currencyNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
        handleCurrencyClick(currencyCodes);
    }

    private void handleCurrencyClick(final String[] currencyCodes) {
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currencyCode = currencyCodes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public void showSaveSuccess() {
        AppUtils.showShortToast(getContext(), "Card created successfully");
    }

    @Override
    public void showSaveError(String msg) {
        AppUtils.showLongToast(getContext(), msg);
    }

    @Override
    public void showCardsView() {
        getActivity().onBackPressed();
    }
}