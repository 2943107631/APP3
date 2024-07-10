package com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.activity.MainActivity3;
import com.example.R;
import com.guanli.MainActivity4;
import com.sqlite.DBHelper1;
import com.user.MainActivity2;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private AppCompatEditText userId;
    private AppCompatEditText password;
    private AppCompatButton login;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        // 获取文件权限
        setPermission();

        // 注册
        register.setOnClickListener(v -> {
            startActivity(new Intent(this, Register.class));
        });

        // 登录
        login.setOnClickListener(v -> {
            if (userId.getText().toString().equals("admin") && password.getText().toString().equals("123456")) {
                startActivity(new Intent(this, MainActivity4.class));
                Toast.makeText(this, "管理员登录成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                DBHelper1 dbHelper1 = new DBHelper1(this);
                SQLiteDatabase db = dbHelper1.getReadableDatabase();
                String selection = "id = ? AND password = ?";
                String[] selectionArgs = {userId.getText().toString(), password.getText().toString()};
                Cursor cursor = db.query("user", null, selection, selectionArgs, null, null, null);
                if (cursor.moveToFirst()) {
                    if (cursor.getString(cursor.getColumnIndex("id")).substring(0, 1).equals("s")) {
                        Toast.makeText(this, "商家登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity3.class);
                        intent.putExtra("id", cursor.getString(cursor.getColumnIndex("id")));
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity2.class);
                        intent.putExtra("id", cursor.getString(cursor.getColumnIndex("id")));
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 初始化组件
    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setVisibility(View.GONE);
        ((TextView) toolbar.getChildAt(1)).setText("登录");
        toolbar.getChildAt(2).setVisibility(View.GONE);

        userId = (AppCompatEditText) findViewById(R.id.userId);
        password = (AppCompatEditText) findViewById(R.id.password);
        login = (AppCompatButton) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
    }

    // 设置文件权限
    private void setPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 设置地址权限
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    3);
        }
    }
}