package com.admin.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.activity.AddJianzhi;
import com.admin.activity.AdminJianzhiDetail;
import com.bean.JianzhiBean;
import com.bumptech.glide.Glide;
import com.example.R;
import com.sqlite.DBHelper2;
import com.util.AdapterUtil;

import java.util.ArrayList;
import java.util.List;

public class AdminJianzhiFragment extends Fragment {

    private AppCompatEditText edit;
    private AppCompatButton search;
    private RecyclerView recy;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_jianzhi, container, false);
        userId = getActivity().getIntent().getStringExtra("id");
        initView(view);

        setRecy("");

        search.setOnClickListener(v -> {
            setRecy(edit.getText().toString());
        });
        return view;
    }

    private void setRecy(String name) {
        List<JianzhiBean> list = new ArrayList<>();
        DBHelper2 dbHelper2 = new DBHelper2(getContext());
        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        String sqlQuery = "SELECT * FROM jianzhi";
        Cursor cursor = db.rawQuery(sqlQuery, null);
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
            if (bean.getTitle().contains(name))
                list.add(bean);
        }

        AdapterUtil<JianzhiBean> adapter = new AdapterUtil<>(R.layout.jianzhi_item, ((data, position, holder) -> {
            LinearLayout layout = holder.getView(R.id.layout);
            ImageView image = holder.getView(R.id.image);
            TextView title = holder.getView(R.id.title);
            TextView druation = holder.getView(R.id.druation);
            TextView type = holder.getView(R.id.type);
            Glide.with(getContext()).load(data.getImage()).skipMemoryCache(true).into(image);
            title.setText(data.getTitle());
            druation.setText("工作时长:" + data.getDruation() + "h");
            type.setText("工作类型:" + data.getType());
            layout.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), AdminJianzhiDetail.class);
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
        ((TextView) toolbar.getChildAt(1)).setText("兼职");
        toolbar.getChildAt(2).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddJianzhi.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        edit = (AppCompatEditText) view.findViewById(R.id.edit);
        search = (AppCompatButton) view.findViewById(R.id.search);
        recy = (RecyclerView) view.findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecy("");
    }
}
