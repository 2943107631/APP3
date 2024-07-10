package com.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.JianzhiBean;
import com.bumptech.glide.Glide;
import com.example.R;
import com.sqlite.DBHelper2;
import com.util.ImageUtil;

public class AdminJianzhiDetail extends AppCompatActivity {

    private ImageView image;
    private TextView title;
    private ImageView touxiang;
    private TextView username;
    private TextView content;
    private TextView location;
    private TextView price;
    private AppCompatButton delete;

    private JianzhiBean bean;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_jianzhi_detail);
        bean = (JianzhiBean) getIntent().getSerializableExtra("bean");
        userId = getIntent().getStringExtra("userId");
        initView();
        initData();

        delete.setOnClickListener(v -> {
            DBHelper2 dbHelper2 = new DBHelper2(getApplicationContext());
            SQLiteDatabase db = dbHelper2.getWritableDatabase();
            String selection = "id=?";
            String[] selectionArgs = {String.valueOf(bean.getId())};
            int deletedRows = db.delete("jianzhi", selection, selectionArgs);
            if (deletedRows > 0) {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        if (bean.getUserId().equals(userId))
            delete.setVisibility(View.VISIBLE);
        else delete.setVisibility(View.GONE);
        touxiang.setImageResource(ImageUtil.getImage());

        Glide.with(this).load(bean.getImage()).into(image);
        title.setText(bean.getTitle());
        username.setText(bean.getUsername());
        content.setText(bean.getContent());
        location.setText("工作地点:" + bean.getLocation());
        price.setText("薪资:" + bean.getPrice() + "￥");
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setOnClickListener(v -> finish());
        ((TextView) toolbar.getChildAt(1)).setText("详细");
        toolbar.getChildAt(2).setVisibility(View.GONE);
        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        touxiang = (ImageView) findViewById(R.id.touxiang);
        username = (TextView) findViewById(R.id.username);
        content = (TextView) findViewById(R.id.content);
        location = (TextView) findViewById(R.id.location);
        price = (TextView) findViewById(R.id.price);
        delete = (AppCompatButton) findViewById(R.id.delete);

    }
}