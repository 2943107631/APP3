package com.admin.activity;

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

import com.bean.JianzhiBean;
import com.bumptech.glide.Glide;
import com.example.R;
import com.sqlite.DBHelper2;
import com.util.AdapterUtil;

import java.util.ArrayList;
import java.util.List;

public class AdminMyRelease extends AppCompatActivity {

    private String myId;
    private RecyclerView recy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_my_release);
        myId = getIntent().getStringExtra("id");
        initView();

        setRecy();
    }

    private void setRecy() {
        List<JianzhiBean> list = new ArrayList<>();
        DBHelper2 dbHelper2 = new DBHelper2(this);
        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        String sqlQuery = "SELECT * FROM jianzhi WHERE userId=?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{myId});
        while (cursor.moveToNext()) {
            JianzhiBean bean = new JianzhiBean(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("userId")),
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("image")),
                    cursor.getString(cursor.getColumnIndex("location")),
                    cursor.getString(cursor.getColumnIndex("price")),
                    cursor.getString(cursor.getColumnIndex("druation")),
                    cursor.getString(cursor.getColumnIndex("type"))
            );
            list.add(bean);
        }

        AdapterUtil<JianzhiBean> adapter = new AdapterUtil<>(R.layout.jianzhi_item, ((data, position, holder) -> {
            LinearLayout layout = holder.getView(R.id.layout);
            ImageView image = holder.getView(R.id.image);
            TextView title = holder.getView(R.id.title);
            TextView druation = holder.getView(R.id.druation);
            TextView type = holder.getView(R.id.type);
            Glide.with(this).load(data.getImage()).skipMemoryCache(true).into(image);
            title.setText(data.getTitle());
            druation.setText("工作时长:" + data.getDruation() + "h");
            type.setText("工作类型:" + data.getType());
            layout.setOnClickListener(v -> {
                Intent intent = new Intent(this, AdminJianzhiDetail.class);
                intent.putExtra("bean", data);
                intent.putExtra("userId", myId);
                startActivity(intent);
            });
        }));
        adapter.listUpdate(list);
        recy.setAdapter(adapter);
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setOnClickListener(v -> finish());
        ((TextView) toolbar.getChildAt(1)).setText("我的发布");
        toolbar.getChildAt(2).setVisibility(View.GONE);
        recy = (RecyclerView) findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(this));
    }
}