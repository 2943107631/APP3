package com.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.JianzhiBean;
import com.bumptech.glide.Glide;
import com.example.R;
import com.util.ImageUtil;

public class JianzhiDetail extends AppCompatActivity {

    private JianzhiBean bean;
    private String userId;
    private ImageView image;
    private TextView title;
    private ImageView touxiang;
    private TextView username;
    private TextView content;
    private TextView location;
    private TextView price;
    private TextView druation;
    private TextView type;
    private Button contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jianzhi_detail);
        bean = (JianzhiBean) getIntent().getSerializableExtra("bean");
        userId = getIntent().getStringExtra("userId");
        initView();
        initData();

        // 联系按钮
        contact.setOnClickListener(v -> {
            Intent intent = new Intent(this, Liaotian.class);
            intent.putExtra("myId", userId);
            intent.putExtra("userId", bean.getUserId());
            intent.putExtra("username", bean.getUsername());
            startActivity(intent);
        });
    }

    private void initData() {
        touxiang.setImageResource(ImageUtil.getImage());
        Glide.with(this).load(bean.getImage()).into(image);

        title.setText(bean.getTitle());
        username.setText(bean.getUsername());
        content.setText(bean.getContent());
        location.setText("工作地点:" + bean.getLocation());
        price.setText("薪资:" + bean.getPrice() + "￥");
        druation.setText("工作时长:" + bean.getDruation() + "h");
        type.setText("工作类型:" + bean.getType());
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
        druation = (TextView) findViewById(R.id.druation);
        type = (TextView) findViewById(R.id.type);
        contact = (Button) findViewById(R.id.contact);
    }
}