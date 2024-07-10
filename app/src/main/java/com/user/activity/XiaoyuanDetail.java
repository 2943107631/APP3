package com.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.Double3;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.JianzhiBean;
import com.bean.XiaoyuanBean;
import com.bumptech.glide.Glide;
import com.example.R;
import com.sqlite.DBHelper2;
import com.sqlite.DBHelper3;
import com.util.ImageUtil;

public class XiaoyuanDetail extends AppCompatActivity {

    private XiaoyuanBean bean;
    private String userId;

    private ImageView image;
    private TextView title;
    private ImageView touxiang;
    private TextView username;
    private TextView content;
    private TextView degree;
    private TextView price;
    private Button contact;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaoyuan_detail);
        bean = (XiaoyuanBean) getIntent().getSerializableExtra("bean");
        userId = getIntent().getStringExtra("userId");
        initView();
        initData();

        // 判断 id 如果是自己发布的 就显示 删除按钮 如果不是 就显示 联系按钮
        if (bean.getUserid().equals(userId)) {
            contact.setVisibility(View.GONE);
            delete.setVisibility(View.VISIBLE);
        } else {
            contact.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);
        }
        // 联系按钮
        contact.setOnClickListener(v -> {
            Intent intent = new Intent(this, Liaotian.class);
            intent.putExtra("myId", userId);
            intent.putExtra("userId", bean.getUserid());
            intent.putExtra("username", bean.getUsername());
            startActivity(intent);
        });

        // 删除按钮
        delete.setOnClickListener(v -> {
            DBHelper3 dbHelper3 = new DBHelper3(getApplicationContext());
            SQLiteDatabase db = dbHelper3.getWritableDatabase();
            String selection = "id=?";
            String[] selectionArgs = {String.valueOf(bean.getId())};
            int deletedRows = db.delete("xiaoyuan", selection, selectionArgs);
            if (deletedRows > 0) {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {

        touxiang.setImageResource(ImageUtil.getImage());
        Glide.with(this).load(bean.getImage()).into(image);

        title.setText(bean.getTitle());
        username.setText(bean.getUsername());
        content.setText(bean.getContent());
        degree.setText(bean.getDegree() + "新");
        price.setText(bean.getPrice() + "￥");
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
        degree = (TextView) findViewById(R.id.degree);
        price = (TextView) findViewById(R.id.price);
        contact = (Button) findViewById(R.id.contact);
        delete = (Button) findViewById(R.id.delete);
    }
}