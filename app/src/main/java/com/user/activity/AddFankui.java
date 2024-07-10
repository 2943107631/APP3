package com.user.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.R;
import com.sqlite.DBHelper5;

public class AddFankui extends AppCompatActivity {

    private EditText content;
    private AppCompatButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fankui);
        initView();

        // 添加反馈 如果成功的话 就会提示 然后返回上一页
        add.setOnClickListener(v -> {
            DBHelper5 dbHelper5 = new DBHelper5(this);
            SQLiteDatabase db = dbHelper5.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("content", content.getText().toString());
            long insert = db.insert("fankui", null, values);
            if (insert != -1) {
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setVisibility(View.GONE);
        ((TextView) toolbar.getChildAt(1)).setText("发布反馈");
        toolbar.getChildAt(2).setVisibility(View.GONE);

        content = (EditText) findViewById(R.id.content);
        add = (AppCompatButton) findViewById(R.id.add);
    }
}