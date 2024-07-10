package com.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper1 extends SQLiteOpenHelper {

    private static final String User = "CREATE TABLE user (\n" +
            "    id TEXT UNIQUE,\n" +
            "    username TEXT,\n" +
            "    phone TEXT,\n" +
            "    school TEXT,\n" +
            "    gender INTEGER,\n" +
            "    password TEXT,\n" +
            "    yyzz TEXT\n" +
            ");";

    public DBHelper1(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
