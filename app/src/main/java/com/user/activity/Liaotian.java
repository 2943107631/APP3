package com.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.JianzhiBean;
import com.bean.LiaotianBean;
import com.example.R;
import com.sqlite.DBHelper1;
import com.sqlite.DBHelper4;
import com.util.AdapterUtil;

import java.util.ArrayList;
import java.util.List;

public class Liaotian extends AppCompatActivity {

    private RecyclerView recy;
    private EditText commentEdit;
    private AppCompatButton send;

    private String myId;
    private String userId;
    private String username;
    private List<LiaotianBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liaotian);
        myId = getIntent().getStringExtra("myId");
        userId = getIntent().getStringExtra("userId");
        username = getIntent().getStringExtra("username");
        initView();

        // 设置子列表内容
        setRecy();

        // 发送聊天内容的按钮
        send.setOnClickListener(v -> {
            DBHelper4 dbHelper4 = new DBHelper4(this);
            SQLiteDatabase db = dbHelper4.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("userId", userId);
            values.put("myId", myId);
            values.put("content", commentEdit.getText().toString());
            long result = db.insert("liaotian", null, values);
            if (result != -1) {
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
                commentEdit.setText("");
                setRecy();
            } else {
                Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setRecy() {
        list.clear();
        DBHelper4 dbHelper4 = new DBHelper4(this);
        SQLiteDatabase db = dbHelper4.getReadableDatabase();
        String sqlQuery = "SELECT * FROM liaotian";
        Cursor cursor = db.rawQuery(sqlQuery,null);
        while (cursor.moveToNext()) {
            LiaotianBean bean = new LiaotianBean(
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("userId")),
                    cursor.getString(cursor.getColumnIndex("myId")),
                    cursor.getString(cursor.getColumnIndex("content"))
            );

//            ？？？
            if ((bean.getUserId().equals(userId) && bean.getMyId().equals(myId)) ||
                    bean.getUserId().equals(myId) && bean.getMyId().equals(userId))
                list.add(bean);
        }

        AdapterUtil<LiaotianBean> adapter = new AdapterUtil<>(R.layout.char_item01, ((data, position, holder) -> {
            LinearLayout layout1 = holder.getView(R.id.layout1);
            TextView username1 = holder.getView(R.id.username1);
            TextView content1 = holder.getView(R.id.content1);
            LinearLayout layout2 = holder.getView(R.id.layout2);
            TextView username2 = holder.getView(R.id.username2);
            TextView content2 = holder.getView(R.id.content2);

            if (data.getMyId().equals(myId)) {
                // 号主发的消息
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                content2.setText(data.getContext());
                username2.setText("我");
            } else {
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
                content1.setText(data.getContext());

                DBHelper1 dbHelper1 = new DBHelper1(this);
                SQLiteDatabase db1 = dbHelper1.getReadableDatabase();
                String sqlQuery1 = "SELECT * FROM user WHERE id = ?";
                Cursor cursor1;
                if (myId.equals(data.getUserId())) {
                    cursor1 = db1.rawQuery(sqlQuery1, new String[]{String.valueOf(data.getMyId())});
                } else {
                    cursor1 = db1.rawQuery(sqlQuery1, new String[]{String.valueOf(data.getUserId())});
                }
                if (cursor1.moveToFirst()) {
                    username1.setText(cursor1.getString(cursor1.getColumnIndex("username")));
                }
            }
        }));
        adapter.listUpdate(list);
        recy.setAdapter(adapter);
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setOnClickListener(v -> finish());
        ((TextView) toolbar.getChildAt(1)).setText("聊天");
        toolbar.getChildAt(2).setVisibility(View.GONE);

        recy = (RecyclerView) findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(this));
        commentEdit = (EditText) findViewById(R.id.comment_edit);
        send = (AppCompatButton) findViewById(R.id.send);
    }
}