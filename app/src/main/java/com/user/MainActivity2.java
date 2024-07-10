package com.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.user.fragment.JIanzhiFragment;
import com.user.fragment.LiaotianFragment;
import com.user.fragment.WodeFragment;
import com.user.fragment.XiaoyuanFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private List<Fragment> list = new ArrayList<>();
    private ViewPager2 vp2;
    private BottomNavigationView jiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();

        // 点击 判断id  如果id是 一样 就会显示id的那个页面
        jiao.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    vp2.setCurrentItem(0);
                    break;
                case R.id.diy:
                    vp2.setCurrentItem(1);
                    break;
                case R.id.jiaoliu:
                    vp2.setCurrentItem(2);
                    break;
                case R.id.person:
                    vp2.setCurrentItem(3);
                    break;
            }
            return true;
        });
    }

    private void initData() {
        list.add(new JIanzhiFragment());
        list.add(new XiaoyuanFragment());
        list.add(new LiaotianFragment());
        list.add(new WodeFragment());
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