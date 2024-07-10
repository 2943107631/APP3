package com.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper2 extends SQLiteOpenHelper {

    private static final String Jianzhi = "CREATE TABLE jianzhi (\n" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    userId INTEGER,\n" +
            "    username TEXT,\n" +
            "    title TEXT,\n" +
            "    content TEXT,\n" +
            "    image TEXT,\n" +
            "    location TEXT,\n" +
            "    price TEXT,\n" +
            "    druation TEXT,\n" +
            "    type TEXT\n" +
            ");";

    public DBHelper2(@Nullable Context context) {
        super(context, "jianzhi.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Jianzhi);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
