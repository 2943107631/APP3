package com.guanli;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.activity.AdminPersonal;
import com.bean.UserBean;
import com.example.R;
import com.sqlite.DBHelper1;
import com.user.activity.Personal;
import com.util.AdapterUtil;

import java.util.ArrayList;
import java.util.List;

public class PsersonFragment extends Fragment {

    private RecyclerView recy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pserson, container, false);
        initView(view);

        setRecy();

        return view;
    }

    private void setRecy() {
        List<UserBean> list = new ArrayList<>();
        DBHelper1 dbHelper = new DBHelper1(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sqlQuery = "SELECT * FROM user";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        while (cursor.moveToNext()) {
            UserBean bean = new UserBean(
                    cursor.getString(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("phone")),
                    cursor.getString(cursor.getColumnIndex("school")),
                    cursor.getInt(cursor.getColumnIndex("id"))
            );
            list.add(bean);
        }

        AdapterUtil<UserBean> adapter = new AdapterUtil<>(R.layout.user_item, ((data, position, holder) -> {
            TextView text1 = holder.getView(R.id.text1);
            TextView text2 = holder.getView(R.id.text2);
            LinearLayout layout = holder.getView(R.id.layout);
            if (data.getId().substring(0, 1).equals("s")) {
                text1.setText("商店:" + data.getUsername());
                text2.setText("商店id:" + data.getId());
            } else {
                text1.setText("学生:" + data.getUsername());
                text2.setText("学号:" + data.getId());
            }
            layout.setOnClickListener(v -> {
                if (data.getId().substring(0,1).equals("s")){
                    Intent intent = new Intent(getContext(), AdminPersonal.class);
                    intent.putExtra("id", data.getId());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), Personal.class);
                    intent.putExtra("id", data.getId());
                    startActivity(intent);
                }
            });
        }));
        adapter.listUpdate(list);
        recy.setAdapter(adapter);
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setVisibility(View.GONE);
        ((TextView) toolbar.getChildAt(1)).setText("用户");
        toolbar.getChildAt(2).setVisibility(View.GONE);
        recy = view.findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}