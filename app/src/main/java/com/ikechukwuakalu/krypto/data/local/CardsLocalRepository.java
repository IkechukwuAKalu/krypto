package com.ikechukwuakalu.krypto.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ikechukwuakalu.krypto.data.Card;
import com.ikechukwuakalu.krypto.data.CardsDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class CardsLocalRepository implements CardsDataSource {

    private DbHelper dbHelper;

    public CardsLocalRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    @Override
    public boolean saveCard(Card card) {
        if (cardExists(card))
            return false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.CardColumns.CRYPTO_CODE, card.getCryptoCode());
        values.put(DbHelper.CardColumns.CURRENCY_CODE, card.getCurrencyCode());
        db.insert(DbHelper.CARDS_TABLE_NAME, DbHelper.CardColumns.CRYPTO_CODE, values);
        db.close();
        return true;
    }

    private boolean cardExists(Card card) {
        boolean cardExists = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectionWhere = DbHelper.CardColumns.CRYPTO_CODE + " = ? AND " +
                DbHelper.CardColumns.CURRENCY_CODE + " = ?";
        String[] selectionArgs = {
                card.getCryptoCode(), card.getCurrencyCode()
        };
        String[] projection = {
                DbHelper.CardColumns._ID,
        };
        Cursor cursor = db.query(
                DbHelper.CARDS_TABLE_NAME, projection, selectionWhere, selectionArgs, null, null, null
        );
        if (cursor.getCount() > 0) {
            cardExists = true;
            cursor.close();
        }
        db.close();
        return cardExists;
    }

    @Override
    public Flowable<List<Card>> loadCards() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DbHelper.CardColumns._ID,
                DbHelper.CardColumns.CRYPTO_CODE,
                DbHelper.CardColumns.CURRENCY_CODE
        };
        Cursor cursor = db.query(
                DbHelper.CARDS_TABLE_NAME, projection, null, null, null, null, null
        );
        List<Card> cards = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Card card = fetchCardRow(cursor);
                cards.add(card);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return Flowable.just(cards);
    }

    private Card fetchCardRow(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(DbHelper.CardColumns._ID));
        String cryptoCode = cursor.getString(cursor.getColumnIndex(DbHelper.CardColumns.CRYPTO_CODE));
        String currencyCode = cursor.getString(cursor.getColumnIndex(DbHelper.CardColumns.CURRENCY_CODE));
        return new Card(id, cryptoCode, currencyCode);
    }

    @Override
    public Flowable<Card> loadCard(String cardId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectionWhere = DbHelper.CardColumns._ID + " = ?";
        String[] selectionArgs = { cardId };
        String[] projection = {
                DbHelper.CardColumns._ID,
                DbHelper.CardColumns.CRYPTO_CODE,
                DbHelper.CardColumns.CURRENCY_CODE
        };
        Cursor cursor = db.query(
                DbHelper.CARDS_TABLE_NAME, projection, selectionWhere, selectionArgs, null, null, null
        );
        if (cursor != null && cursor.moveToFirst()) {
            Card card = fetchCardRow(cursor);
            cursor.close();
            return Flowable.just(card);
        }
        db.close();
        return Flowable.empty();
    }

    @Override
    public void deleteCard(Card card) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectionWhere = DbHelper.CardColumns._ID + " = ?";
        String[] selectionArgs = {
                String.valueOf(card.getId())
        };
        db.delete(DbHelper.CARDS_TABLE_NAME, selectionWhere, selectionArgs);
        db.close();
    }

    @Override
    public void deleteAllCards() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DbHelper.CARDS_TABLE_NAME, null, null);
        db.close();
    }
}
