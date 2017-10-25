package com.ikechukwuakalu.krypto.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

class DbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "Krypto_app.db";
    private static final int DB_VERSION = 1;

    static final String CARDS_TABLE_NAME = "cards_table";

    private static final String CREATE_CARDS_TABLE = "CREATE TABLE IF NOT EXISTS " + CARDS_TABLE_NAME + " (" +
            CardColumns._ID + " INTEGER NOT NULL PRIMARY KEY, " +
            CardColumns.CRYPTO_CODE + " VARCHAR(5) NOT NULL, " +
            CardColumns.CURRENCY_CODE + " VARCHAR(5) NOT NULL)";

    private static final String DELETE_CARDS_TABLE = "DELETE TABLE IF EXISTS " + CARDS_TABLE_NAME;

    DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CARDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_CARDS_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    static class CardColumns implements BaseColumns {
        static final String CRYPTO_CODE = "crypto_code";
        static final String CURRENCY_CODE = "currency_code";
    }
}
