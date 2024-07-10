package com.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper3 extends SQLiteOpenHelper {

    private static final String Xiaoyuan = "CREATE TABLE xiaoyuan (\n" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    userId TEXT,\n" +
            "    username TEXT,\n" +
            "    title TEXT,\n" +
            "    content TEXT,\n" +
            "    image TEXT,\n" +
            "    degree TEXT,\n" +
            "    price TEXT\n" +
            ");";

    public DBHelper3(@Nullable Context context) {
        super(context, "xiaoyuan.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Xiaoyuan);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
