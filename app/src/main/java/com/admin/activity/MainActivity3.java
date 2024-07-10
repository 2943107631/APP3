package com.admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.admin.fragment.AdminJianzhiFragment;
import com.admin.fragment.AdminLiaotianFragment;
import com.admin.fragment.AdminWodeFragment;
import com.example.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private List<Fragment> list = new ArrayList<>();
    private ViewPager2 vp2;
    private BottomNavigationView jiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
        initData();

        jiao.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    vp2.setCurrentItem(0);
                    break;
                case R.id.jiaoliu:
                    vp2.setCurrentItem(1);
                    break;
                case R.id.person:
                    vp2.setCurrentItem(2);
                    break;
            }
            return true;
        });
    }

    private void initData() {
        list.add(new AdminJianzhiFragment());
        list.add(new AdminLiaotianFragment());
        list.add(new AdminWodeFragment());
        vp2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return list.get(position);
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }

    private void initView() {
        vp2 = findViewById(R.id.vp2);
        vp2.setOffscreenPageLimit(4);
        vp2.setUserInputEnabled(false);
        jiao = (BottomNavigationView) findViewById(R.id.jiao);
    }
}