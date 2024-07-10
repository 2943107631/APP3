package com.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.sqlite.DBHelper1;

public class Password extends AppCompatActivity {

    private AppCompatEditText oldPassword;
    private AppCompatEditText newPassword;
    private AppCompatEditText newPasswordd;
    private AppCompatButton confim;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        id = getIntent().getStringExtra("id");
        initView();

        // 修改密码的按钮
        confim.setOnClickListener(v -> {
            if (newPasswordd.getText().toString().equals(newPassword.getText().toString())) {
                DBHelper1 dbHelper1 = new DBHelper1(this);
                SQLiteDatabase db = dbHelper1.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("password", newPassword.getText().toString());
                int rowsUpdated = db.update("user", values, "id=? AND password=?",
                        new String[]{String.valueOf(id), oldPassword.getText().toString()});
                if (rowsUpdated > 0) {
                    finish();
                    Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "密码修改失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "两次密码不一样", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setOnClickListener(v -> finish());
        ((TextView) toolbar.getChildAt(1)).setText("修改密码");
        toolbar.getChildAt(2).setVisibility(View.GONE);

        oldPassword = (AppCompatEditText) findViewById(R.id.oldPassword);
        newPassword = (AppCompatEditText) findViewById(R.id.newPassword);
        newPasswordd = (AppCompatEditText) findViewById(R.id.newPasswordd);
        confim = (AppCompatButton) findViewById(R.id.confim);
    }
}