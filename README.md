# 引入

## Step 1. Add the JitPack repository to your build file

  Add it in your root build.gradle at the end of repositories:
  
      allprojects {
        repositories {
          ...
          maven { url 'https://jitpack.io' }
        }
      }
    
## Step 2. Add the dependency

    dependencies {
            implementation 'com.github.sinothk:SwitchTabView:1.19.0113'
    }


# 使用

## xml

    <com.sinothk.switchTabView.style1.segement.SegmentView
        android:id="@+id/sv_segement0"
        android:layout_width="90dp"
        android:layout_height="27dp"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:layout_marginTop="10dp"
        app:lib_ui_view_segementv_borderWidth="1dp"
        app:lib_ui_view_segementv_colorMain="@color/lib_ui_common_color_accent"
        app:lib_ui_view_segementv_colorSub="#FFF"
        app:lib_ui_view_segementv_divideWidth="1dp"
        app:lib_ui_view_segementv_radius="13dp"
        app:lib_ui_view_segementv_textSize="12dp" />

## java
    
    SegmentView segment0 = (SegmentView) findViewById(R.id.sv_segement0);
    segment0.setTitles(Arrays.asList("男", "女"));

    segment0.setOnSelectedListener(new SegmentView.OnSelectedListener() {
        @Override
        public void onSelected(int index) {
            Toast.makeText(TabActivity.this, "" + index, Toast.LENGTH_SHORT).show();
        }
    });
    
![]()
https://github.com/sinothk/SwitchTabView/blob/master/app/imgs/QQ20181009163635.png

# tab list
 ## xml
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context=".TabTestMainActivity">

        <com.sinothk.switchTabView.style1.ScrollTab
            android:id="@+id/stab_tab01"
            android:layout_width="match_parent"
            android:layout_height="42dp"
            android:background="#FFFFFF"
            android:orientation="vertical"

            app:lib_ui_view_stab_text_color="#888888"

            app:lib_ui_view_stab_avag="false"

            app:lib_ui_view_stab_indicatorPadding="2dp"
            app:lib_ui_view_stab_indicatorRadius="1.5dp"
            app:lib_ui_view_stab_indicatorType="translation"
            app:lib_ui_view_stab_indicatorWeight="5dp"
            app:lib_ui_view_stab_indicatorWidth="30dp"

            app:lib_ui_view_stab_type="view_group" />

        <!--app:lib_ui_view_stab_text_color_selected="@color/lib_ui_color_yellow"-->
        <!--app:lib_ui_view_stab_indicatorColor="@color/lib_ui_color_yellow"-->

        <android.support.v4.view.ViewPager
            android:id="@+id/pager0"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </LinearLayout>
 ## java
 
     public class TabTestMainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tab_test_main);

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

            ScrollTab tab = (ScrollTab) findViewById(R.id.stab_tab01);
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
