package com.example.loginlianxi.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.loginlianxi.Fragment.Fragment_Home;
import com.example.loginlianxi.Fragment.Fragment_My;
import com.example.loginlianxi.R;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

public class SucceedActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private RadioGroup group;
    private List<Fragment> list;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succeed);

        viewPager = findViewById(R.id.viewPager);
        group = findViewById(R.id.group);

        list = new ArrayList<>();
        list.add(new Fragment_Home());
        list.add(new Fragment_My());
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {

                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.btn_my:
                        viewPager.setCurrentItem(1);
                        break;
                        default:
                            break;
                }
            }
        });
    }

    public String getrequest(){
         return name;
    }

}

