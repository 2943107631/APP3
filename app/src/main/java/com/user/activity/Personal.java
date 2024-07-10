package com.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.sqlite.DBHelper1;

public class Personal extends AppCompatActivity {

    private AppCompatEditText username;
    private AppCompatEditText phone;
    private AppCompatEditText school;
    private RadioButton man;
    private RadioButton woman;
    private AppCompatButton confim;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        id = getIntent().getStringExtra("id");
        initView();
        setData();

        // 修改个人信息的按钮
        confim.setOnClickListener(v -> {
            DBHelper1 dbHelper1 = new DBHelper1(this);
            SQLiteDatabase db = dbHelper1.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username", username.getText().toString());
            values.put("gender", man.isChecked() ? 0 : 1);
            values.put("school", school.getText().toString());
            values.put("phone", phone.getText().toString());
            int rowsUpdated = db.update("user", values, "id=?", new String[]{String.valueOf(id)});
            if (rowsUpdated > 0) {
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData() {
        DBHelper1 dbHelper1 = new DBHelper1(this);
        SQLiteDatabase db = dbHelper1.getWritableDatabase();
        String sqlQuery = "SELECT * FROM user WHERE id = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            username.setText(cursor.getString(cursor.getColumnIndex("username")));
            phone.setText(cursor.getString(cursor.getColumnIndex("phone")));
            school.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("school"))));
            int gender = cursor.getInt(cursor.getColumnIndex("gender"));
            if (gender == 0) {
                man.setChecked(true);
            } else if (gender == 1) {
                woman.setChecked(true);
            }
        }
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setOnClickListener(v -> finish());
        ((TextView) toolbar.getChildAt(1)).setText("个人信息");
        toolbar.getChildAt(2).setVisibility(View.GONE);

        username = (AppCompatEditText) findViewById(R.id.username);
        phone = (AppCompatEditText) findViewById(R.id.phone);
        school = (AppCompatEditText) findViewById(R.id.school);
        man = (RadioButton) findViewById(R.id.man);
        woman = (RadioButton) findViewById(R.id.woman);
        confim = (AppCompatButton) findViewById(R.id.confim);
    }
}