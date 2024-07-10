package com.admin.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bean.LiaotianBean;
import com.example.R;
import com.sqlite.DBHelper1;
import com.sqlite.DBHelper4;
import com.user.activity.Liaotian;
import com.util.AdapterUtil;
import com.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class AdminLiaotianFragment extends Fragment {

    private RecyclerView recy;
    private String myId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_liaotian, container, false);
        myId = getActivity().getIntent().getStringExtra("id");
        initView(view);

        setRecy();

        return view;
    }

    private void setRecy() {
        List<LiaotianBean> list = new ArrayList<>();
        DBHelper4 dbHelper4 = new DBHelper4(getContext());
        SQLiteDatabase db = dbHelper4.getReadableDatabase();
        String sqlQuery = "SELECT * FROM liaotian WHERE myId=? OR userId=? GROUP BY userId;";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{myId, myId});
        while (cursor.moveToNext()) {
            LiaotianBean bean = new LiaotianBean(
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("userId")),
                    cursor.getString(cursor.getColumnIndex("myId")),
                    cursor.getString(cursor.getColumnIndex("content"))
            );
            list.add(bean);
        }

        AdapterUtil<LiaotianBean> adapter = new AdapterUtil<>(R.layout.liaotian_item, ((data, position, holder) -> {
            LinearLayout layout = (LinearLayout) holder.getView(R.id.layout);
            ImageView touxiang = (ImageView) holder.getView(R.id.touxiang);
            TextView userId = (TextView) holder.getView(R.id.userId);
            touxiang.setImageResource(ImageUtil.getImage());

            DBHelper1 dbHelper1 = new DBHelper1(getContext());
            SQLiteDatabase db1 = dbHelper1.getReadableDatabase();
            String sqlQuery1 = "SELECT * FROM user WHERE id = ?";
            Cursor cursor1 = db1.rawQuery(sqlQuery1, new String[]{String.valueOf(data.getMyId())});
            if (cursor1.moveToFirst()) {
                userId.setText(cursor1.getString(cursor1.getColumnIndex("username")));
            }

            layout.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), Liaotian.class);
                intent.putExtra("myId", myId);
                if (myId.equals(data.getMyId()))
                    intent.putExtra("userId", data.getUserId());
                else
                    intent.putExtra("userId", data.getMyId());
                intent.putExtra("username", data.getUsername());
                startActivity(intent);
            });
        }));
        adapter.listUpdate(list);
        recy.setAdapter(adapter);

    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setVisibility(View.GONE);
        ((TextView) toolbar.getChildAt(1)).setText("聊天");
        toolbar.getChildAt(2).setVisibility(View.GONE);

        recy = view.findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}