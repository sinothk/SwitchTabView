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

public class TabSelectedMainActivity extends AppCompatActivity {

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_selected_main);

        index = 1;

        initTab(Arrays.asList("推荐", "廉政"));
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

        final ScrollTab tab = (ScrollTab) findViewById(R.id.stab_tab01);

        tab.setTitles(titles, 100)
                .setViewPager(pager)
                .setOnTabListener(new ScrollTab.OnTabListener() {
                    @Override
                    public void onChange(int index, View v) {
                        pager.setCurrentItem(index, true);
                    }
                });
    }
}
