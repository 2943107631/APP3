package com.user.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.activity.AddJianzhi;
import com.bean.JianzhiBean;
import com.bean.XiaoyuanBean;
import com.bumptech.glide.Glide;
import com.example.R;
import com.sqlite.DBHelper2;
import com.sqlite.DBHelper3;
import com.user.activity.AddXiaoyuan;
import com.user.activity.JianzhiDetail;
import com.user.activity.XiaoyuanDetail;
import com.util.AdapterUtil;
import com.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class XiaoyuanFragment extends Fragment {

    private RecyclerView recy;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xiaoyuan, container, false);
        userId = getActivity().getIntent().getStringExtra("id");
        initView(view);

        setRecy();
        return view;
    }

    private void setRecy() {
        List<XiaoyuanBean> list = new ArrayList<>();
        DBHelper3 dbHelper3 = new DBHelper3(getContext());
        SQLiteDatabase db = dbHelper3.getReadableDatabase();
        String sqlQuery = "SELECT * FROM xiaoyuan";
        Cursor cursor = db.rawQuery(sqlQuery, null);
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
                Intent intent = new Intent(getContext(), XiaoyuanDetail.class);
                intent.putExtra("bean", data);
                intent.putExtra("userId", userId);
                startActivity(intent);
            });
        }));
        adapter.listUpdate(list);
        recy.setAdapter(adapter);
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setVisibility(View.GONE);
        ((TextView) toolbar.getChildAt(1)).setText("校园");
        toolbar.getChildAt(2).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddXiaoyuan.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        recy = (RecyclerView) view.findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecy();
    }
}