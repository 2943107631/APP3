package com.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bean.XiaoyuanBean;
import com.bumptech.glide.Glide;
import com.example.R;
import com.sqlite.DBHelper3;
import com.util.AdapterUtil;

import java.util.ArrayList;
import java.util.List;

public class MyRelease extends AppCompatActivity {

    private String myId;
    private RecyclerView recy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_release);
        myId=getIntent().getStringExtra("id");
        initView();

        // 设置子列表内容
        setRecy();
    }

    private void setRecy() {
        List<XiaoyuanBean> list = new ArrayList<>();
        DBHelper3 dbHelper3 = new DBHelper3(this);
        SQLiteDatabase db = dbHelper3.getReadableDatabase();
        String sqlQuery = "SELECT * FROM xiaoyuan WHERE userId=?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{myId});
        while (cursor.moveToNext()) {
            XiaoyuanBean bean = new XiaoyuanBean(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("userId")),
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("image")),
                    cursor.getString(cursor.getColumnIndex("degree")),
                    cursor.getString(cursor.getColumnIndex("price"))
            );
            list.add(bean);
        }

        AdapterUtil<XiaoyuanBean> adapter = new AdapterUtil<>(R.layout.xiaoyuan_item, ((data, position, holder) -> {
            LinearLayout layout = holder.getView(R.id.layout);
            ImageView image = holder.getView(R.id.image);
            TextView title = holder.getView(R.id.title);
            TextView degree = holder.getView(R.id.degree);
            TextView price = holder.getView(R.id.price);

            Glide.with(this).load(data.getImage()).into(image);
            title.setText(data.getTitle());
            degree.setText(data.getDegree() + "新");
            price.setText(data.getPrice() + "￥");
            layout.setOnClickListener(v -> {
                Intent intent = new Intent(this, XiaoyuanDetail.class);
                intent.putExtra("bean", data);
                intent.putExtra("userId", myId);
                startActivity(intent);
            });
        }));
        adapter.listUpdate(list);
        recy.setAdapter(adapter);
    }

    private void initView() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setOnClickListener(v -> finish());
        ((TextView)toolbar.getChildAt(1)).setText("我的发布");
        toolbar.getChildAt(2).setVisibility(View.GONE);
        recy = (RecyclerView) findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(this));
    }
}