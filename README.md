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
            implementation 'com.github.sinothk:SwitchTabView:1.0.1009'
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
