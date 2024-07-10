package com;

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

public class Register extends AppCompatActivity {

    private AppCompatEditText userId;
    private AppCompatEditText username;
    private AppCompatEditText school;
    private AppCompatEditText password;
    private AppCompatEditText passwordd;
    private AppCompatButton register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // 初始化组件
        initView();

        // 注册账号
        register.setOnClickListener(v -> {
            if (userId.getText().toString().isEmpty()) {
                Toast.makeText(this, "请输入学号", Toast.LENGTH_SHORT).show();
            } else if (username.getText().toString().isEmpty()) {
                Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            } else if (school.getText().toString().isEmpty()) {
                Toast.makeText(this, "请输入学校", Toast.LENGTH_SHORT).show();
            } else if (password.getText().toString().isEmpty()) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            } else if (!password.getText().toString().equals(passwordd.getText().toString())) {
                Toast.makeText(this, "两次密码不匹配", Toast.LENGTH_SHORT).show();
            } else {
                DBHelper1 dbHelper1 = new DBHelper1(this);
                SQLiteDatabase db = dbHelper1.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("id", userId.getText().toString());
                values.put("username", username.getText().toString());
                values.put("school", school.getText().toString());
                values.put("password", password.getText().toString());
                long insertResult = db.insert("user", null, values);
                if (insertResult != -1) {
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                }
                db.close();
                dbHelper1.close();
            }
        });
    }

    // 初始化组件
    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setOnClickListener(v -> finish());
        ((TextView) toolbar.getChildAt(1)).setText("注册");
        toolbar.getChildAt(2).setVisibility(View.GONE);

        userId = (AppCompatEditText) findViewById(R.id.userId);
        username = (AppCompatEditText) findViewById(R.id.username);
        school = (AppCompatEditText) findViewById(R.id.school);
        password = (AppCompatEditText) findViewById(R.id.password);
        passwordd = (AppCompatEditText) findViewById(R.id.passwordd);
        register = (AppCompatButton) findViewById(R.id.register);
    }
}