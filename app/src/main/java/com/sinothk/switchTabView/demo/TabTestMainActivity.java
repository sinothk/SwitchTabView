package com.sinothk.switchTabView.demo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sinothk.switchTabView.style1.ScrollTab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabTestMainActivity extends AppCompatActivity {

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test_main);

        index = 6;

        initTab(Arrays.asList("推荐", "廉政", "监察", "队伍", "门户", "监察监察监察", "监察", "廉政", "监察", "队伍", "门户", "监察", "监察", "廉政", "监察", "队伍", "门户", "监察", "监察"));
    }

    private void initTab(List<String> titles) {

        final ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            TabFragment fragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragments.get(arg0);
            }
        };

        final ViewPager pager = (ViewPager) findViewById(R.id.pager0);
        pager.setOffscreenPageLimit(titles.size() - 1);
        pager.setAdapter(fragmentPagerAdapter);
//        pager.addOnPageChangeListener(this);

        final ScrollTab tab = (ScrollTab) findViewById(R.id.stab_tab01);

        tab.setTitles(titles);
        tab.setViewPager(pager);

        tab.setOnTabListener(new ScrollTab.OnTabListener() {
            @Override
            public void onChange(int index, View v) {
                pager.setCurrentItem(index, true);
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pager.setCurrentItem(6);
//                        tab.onPageSelected(6);
//                    }
//                });
//            }
//        }, 1000);
    }
}
