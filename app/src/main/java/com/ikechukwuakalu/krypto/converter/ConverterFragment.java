package com.ikechukwuakalu.krypto.converter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ikechukwuakalu.krypto.BaseFragment;
import com.ikechukwuakalu.krypto.R;
import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.utils.AppUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class ConverterFragment extends BaseFragment implements ConverterContract.View {

    private ConverterPresenter presenter;

    private EditText cryptoEditValue, currencyEditValue;
    private TextView cryptoCode, currencyCode, currencyValue;
    TextView clearFields;
    AppCompatButton converterBtn;
    private ProgressBar progressBar;
    private LinearLayout converterView;

    TextWatcher cryptoWatcher, currencyWatcher;

    public ConverterFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = ((ConverterActivity)getActivity()).presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.converter_frag, container, false);

        cryptoEditValue = v.findViewById(R.id.cryptoEditValue);
        currencyEditValue = v.findViewById(R.id.currencyEditValue);
        cryptoCode = v.findViewById(R.id.cryptoCode);
        currencyCode = v.findViewById(R.id.currencyCode);
        currencyValue = v.findViewById(R.id.currencyValue);
        clearFields = v.findViewById(R.id.clearFields);
        converterBtn = v.findViewById(R.id.converterBtn);
        progressBar = v.findViewById(R.id.converterProgBar);
        converterView = v.findViewById(R.id.converterView);

        clearFields.setOnClickListener(clearFieldsBtnClickListener());
        converterBtn.setOnClickListener(conversionBtnClickListener());

        setupCryptoWatcher();
        setupCurrencyWatcher();

        return v;
    }

    private View.OnClickListener clearFieldsBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCryptoValue();
                clearCurrencyValue();
            }
        };
    }

    private View.OnClickListener conversionBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crypto = cryptoEditValue.getText().toString().trim();
                String currency = currencyEditValue.getText().toString().trim();
                presenter.performConversion(crypto, currency);
            }
        };
    }

    private void setupCryptoWatcher() {
        cryptoWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    disableCurrency();
                else enableCurrency();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    private void setupCurrencyWatcher() {
        currencyWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    disableCrypto();
                else
                    enableCrypto();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        cryptoEditValue.addTextChangedListener(cryptoWatcher);
        currencyEditValue.addTextChangedListener(currencyWatcher);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachView();
        cryptoEditValue.removeTextChangedListener(cryptoWatcher);
        currencyEditValue.removeTextChangedListener(currencyWatcher);
    }

    @Override
    public void showLoading() {
        converterView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        converterView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    @Override
    public void showCurrencyValue(String value) {
        DecimalFormat decimalFormatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols formatSymbols = decimalFormatter.getDecimalFormatSymbols();
        formatSymbols.setCurrencySymbol("");
        decimalFormatter.setDecimalFormatSymbols(formatSymbols);
        decimalFormatter.setMinimumFractionDigits(2);
        String currValue = decimalFormatter.format(Double.valueOf(value));
        currencyEditValue.setText(currValue);
    }

    @Override
    public void showCryptoValue(String value) {
        DecimalFormat decimalFormatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols formatSymbols = decimalFormatter.getDecimalFormatSymbols();
        formatSymbols.setCurrencySymbol("");
        decimalFormatter.setDecimalFormatSymbols(formatSymbols);
        decimalFormatter.setMinimumFractionDigits(8);
        String cryptoValue = decimalFormatter.format(Double.valueOf(value));
        cryptoEditValue.setText(cryptoValue);
    }

    @Override
    public void showConversionRate(Card card) {
        String cryptoText = "1 " + card.getCryptoCode();
        cryptoCode.setText(cryptoText);
        String currencyCodeText = card.getCurrencyCode();
        currencyCode.setText(currencyCodeText);

        DecimalFormat decimalFormatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols formatSymbols = decimalFormatter.getDecimalFormatSymbols();
        formatSymbols.setCurrencySymbol("");
        decimalFormatter.setDecimalFormatSymbols(formatSymbols);
        String currencyValueText = decimalFormatter.format(Double.valueOf(card.getValue()));
        currencyValue.setText(currencyValueText);
    }

    @Override
    public void showError(String msg) {
        AppUtils.showLongToast(getContext(), msg);
    }

    @Override
    public void enableCrypto() {
        cryptoEditValue.setEnabled(true);
    }

    @Override
    public void disableCrypto() {
        cryptoEditValue.setEnabled(false);
    }

    @Override
    public void enableCurrency() {
        currencyEditValue.setEnabled(true);
    }

    @Override
    public void disableCurrency() {
        currencyEditValue.setEnabled(false);
    }

    @Override
    public void clearCryptoValue() {
        cryptoEditValue.setText("");
    }

    @Override
    public void clearCurrencyValue() {
        currencyEditValue.setText("");
    }
}
