package com.ikechukwuakalu.krypto.cards;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikechukwuakalu.krypto.R;
import com.ikechukwuakalu.krypto.data.Card;

import java.util.Collections;
import java.util.List;

class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder>{

    private List<Card> cards = Collections.emptyList();

    private View.OnClickListener cardClickListener;
    private View.OnClickListener optionsClickListener;

    CardsAdapter(List<Card> cards, View.OnClickListener cardClickListener, View.OnClickListener optionsClickListener) {
        this.cards = cards;
        this.cardClickListener = cardClickListener;
        this.optionsClickListener = optionsClickListener;
    }

    @Override
    public CardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_item, parent, false);
        return new CardsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardsViewHolder holder, int position) {
        holder.bindTo(cards.get(position));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class CardsViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView cryptoLogo;
        ImageView cardOptions;
        TextView currencyName;

        CardsViewHolder(View itemView) {
            super(itemView);
            cryptoLogo = itemView.findViewById(R.id.cryptoImage);
            currencyName = itemView.findViewById(R.id.currencyName);
            cardOptions = itemView.findViewById(R.id.cardOptions);

            itemView.setOnClickListener(cardClickListener);
            cardOptions.setOnClickListener(optionsClickListener);
        }

        void bindTo(Card card) {
            // Keep reference to the card
            itemView.setTag(card);
            cardOptions.setTag(card);

            int logo;
            if (card.getCryptoCode().equalsIgnoreCase("BTC"))
                logo = R.drawable.ic_btc;
            else
                logo = R.drawable.ic_ethereum;
            cryptoLogo.setImageResource(logo);
            currencyName.setText(card.getCurrencyCode());
        }
    }
}
