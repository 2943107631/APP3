package com.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper5 extends SQLiteOpenHelper {

    private static final String Fankui = "CREATE TABLE fankui (\n" +
            "    content TEXT\n" +
            ");";

    public DBHelper5(@Nullable Context context) {
        super(context, "fankui.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Fankui);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
