package com.guanli;

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
import android.widget.TextView;

import com.example.R;
import com.sqlite.DBHelper1;
import com.sqlite.DBHelper5;
import com.util.AdapterUtil;
import com.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class FankuiFragment extends Fragment {

    private RecyclerView recy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fankui, container, false);
        initView(view);

        setRecy();

        return view;
    }

    private void setRecy() {
        List<String> list = new ArrayList<>();
        DBHelper5 dBHelper5 = new DBHelper5(getContext());
        SQLiteDatabase db = dBHelper5.getReadableDatabase();
        String sqlQuery = "SELECT * FROM fankui";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex("content")));
        }

        AdapterUtil<String> adapter = new AdapterUtil<>(R.layout.fankui_item, ((data, position, holder) -> {
            TextView text = holder.getView(R.id.text);
            text.setText(data);
        }));
        adapter.listUpdate(list);
        recy.setAdapter(adapter);
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setVisibility(View.GONE);
        ((TextView) toolbar.getChildAt(1)).setText("反馈");
        toolbar.getChildAt(2).setVisibility(View.GONE);
        recy = view.findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}