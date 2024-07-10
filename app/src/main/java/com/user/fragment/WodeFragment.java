package com.user.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MainActivity;
import com.example.R;
import com.sqlite.DBHelper1;
import com.user.activity.AddFankui;
import com.user.activity.MyRelease;
import com.user.activity.Password;
import com.user.activity.Personal;
import com.util.ImageUtil;

public class WodeFragment extends Fragment {

    private ImageView image;
    private TextView username;
    private TextView userId;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private LinearLayout layout4;
    private AppCompatButton exit;
    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wode, container, false);
        id = getActivity().getIntent().getStringExtra("id");
        initView(view);
        setData();

        layout1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Personal.class);
            intent.putExtra("id", id);
            getActivity().startActivity(intent);
        });
        layout2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Password.class);
            intent.putExtra("id", id);
            getActivity().startActivity(intent);
        });
        layout3.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyRelease.class);
            intent.putExtra("id", id);
            getActivity().startActivity(intent);
        });
        layout4.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddFankui.class);
            getActivity().startActivity(intent);
        });

        exit.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        });
        return view;
    }

    private void setData() {
        DBHelper1 dbHelper = new DBHelper1(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sqlQuery = "SELECT * FROM user WHERE id = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            userId.setText("学号:" + cursor.getString(cursor.getColumnIndex("id")));
            username.setText(cursor.getString(cursor.getColumnIndex("username")));
            image.setImageResource(ImageUtil.getImage());
        }
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setVisibility(View.GONE);
        ((TextView) toolbar.getChildAt(1)).setText("我的");
        toolbar.getChildAt(2).setVisibility(View.GONE);

        image = (ImageView) view.findViewById(R.id.image);
        username = (TextView) view.findViewById(R.id.username);
        userId = (TextView) view.findViewById(R.id.id);
        layout1 = (LinearLayout) view.findViewById(R.id.layout1);
        layout2 = (LinearLayout) view.findViewById(R.id.layout2);
        layout3 = (LinearLayout) view.findViewById(R.id.layout3);
        layout4 = (LinearLayout) view.findViewById(R.id.layout4);
        exit = (AppCompatButton) view.findViewById(R.id.exit);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
}