package com.sinothk.switchTabView.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sinothk.switchTabView.style1.ScrollTab;
import com.sinothk.switchTabView.style1.segement.SegmentView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TabActivity
 * Created by D on 2017/8/27.
 */
public class TabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView();

        findViewById(R.id.demoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TabActivity.this, TabSelectedMainActivity.class));
            }
        });
    }

    private void initView() {
        SegmentView segment0 = (SegmentView) findViewById(R.id.sv_segement0);
        segment0.setTitles(Arrays.asList("男", "女"));

        segment0.setOnSelectedListener(new SegmentView.OnSelectedListener() {
            @Override
            public void onSelected(int index) {
                Toast.makeText(TabActivity.this, "" + index, Toast.LENGTH_SHORT).show();
            }
        });


        ScrollTab[] scrollTab0 = new ScrollTab[]{(ScrollTab) findViewById(R.id.stab_tab00), (ScrollTab) findViewById(R.id.stab_tab01), (ScrollTab) findViewById(R.id.stab_tab02)};
        ScrollTab[] scrollTab1 = new ScrollTab[]{(ScrollTab) findViewById(R.id.stab_tab10), (ScrollTab) findViewById(R.id.stab_tab11), (ScrollTab) findViewById(R.id.stab_tab12)};

        ViewPager pager0 = (ViewPager) findViewById(R.id.pager0);
        ViewPager pager1 = (ViewPager) findViewById(R.id.pager1);

        initTab(scrollTab0, pager0, Arrays.asList("图片", "视频", "收藏"));

        initTab(scrollTab1, pager1, Arrays.asList("Peach", "Lemon", "Watermelon", "Pear", "Avocado",
                "Banana", "Grape", "Apricot", "Orange", "Kumquat"));
    }

    private void initTab(ScrollTab[] tabs, final ViewPager pager, List<String> titles) {
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
        pager.setOffscreenPageLimit(titles.size() - 1);
        pager.setAdapter(fragmentPagerAdapter);
        pager.addOnPageChangeListener(this);
        for (ScrollTab tab : tabs) {
            tab.setTitles(titles);
            tab.setNumber(1, "9", View.VISIBLE);//设置数字红点
            tab.setViewPager(pager);
            tab.setOnTabListener(new ScrollTab.OnTabListener() {
                @Override
                public void onChange(int index, View v) {
                    pager.setCurrentItem(index, true);
                }
            });
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}