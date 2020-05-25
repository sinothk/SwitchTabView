package com.sinothk.switchTabView.style1;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.sinothk.switchTabView.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  创建:  LiangYT 2018/9/12/012 on 17:06
 *  项目: SwitchTabViewLib
 *  描述:
 *  更新:
 * <pre>
 */
public class ScrollTab extends HorizontalScrollView implements View.OnClickListener, ViewPager.OnPageChangeListener {
    /**
     * TAB类型
     */
    private final int TYPE_VIEW = 0;
    private final int TYPE_VIEW_GROUP = 1;

    /**
     * 指示器类型
     */
    private final int TYPE_INDICATOR_TREND = 0;
    private final int TYPE_INDICATOR_TRANSLATION = 1;
    private final int TYPE_INDICATOR_NONE = 2;

    private int width;
    private int height;

    private Context context;
    private RectF rectF;
    private Paint paint;

    private int type;
    private boolean isAvag;
    private float padding;//item内部左右预留间距
    private String strTitles;
    private int indicatorType;
    private int indicatorColor;
    private float indicatorWidth;
    private float indicatorWeight;
    private float indicatorRadius;
    private float indicatorPadding;

    // 文字
    private int textColor;
    private int textFocusColor;
    private float textSize;

    private ArrayList<TabItem> items;
    private ArrayList<View> tabs;
    private int count;
    private int position = 0;
    private float positionOffset;
    private boolean isFirst = true;
    private ViewPager viewPager;
    private OnTabListener listener;

    public ScrollTab(Context context) {
        this(context, null);
    }

    public ScrollTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        init(context);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.lib_ui_view_ScrollTab);
        type = typedArray.getInt(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_type, TYPE_VIEW);
        isAvag = typedArray.getBoolean(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_avag, false);
        padding = typedArray.getDimension(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_padding, UIUtil.dip2px(context, 12));
        strTitles = typedArray.getString(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_titles);
        indicatorType = typedArray.getInt(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_indicatorType, TYPE_INDICATOR_TREND);
        indicatorColor = typedArray.getColor(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_indicatorColor, ContextCompat.getColor(context, R.color.colorPrimary));
        indicatorWidth = typedArray.getDimension(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_indicatorWidth, UIUtil.dip2px(context, 30));
        indicatorWeight = typedArray.getDimension(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_indicatorWeight, UIUtil.dip2px(context, 1));
        indicatorRadius = typedArray.getDimension(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_indicatorRadius, UIUtil.dip2px(context, 0.5f));
        indicatorPadding = typedArray.getDimension(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_indicatorPadding, UIUtil.dip2px(context, 5));

        // 文本
        textColor = typedArray.getColor(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_text_color, ContextCompat.getColor(context, R.color.lib_ui_common_color_text));
        textFocusColor = typedArray.getColor(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_text_color_selected, ContextCompat.getColor(context, R.color.colorPrimary));
        textSize = typedArray.getDimension(R.styleable.lib_ui_view_ScrollTab_lib_ui_view_stab_text_size, 0);

        typedArray.recycle();
    }

    private void init(Context context) {// 2
        this.context = context;
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setFillViewport(isAvag);
        rectF = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(indicatorColor);

        tabs = new ArrayList<>();
        // xml中添加标题方式
        items = new ArrayList<>();
        if (!TextUtils.isEmpty(strTitles)) {
            String[] strs = strTitles.split(";");
            for (String t : strs) {
                items.add(new TabItem(t, ""));
            }
        }
    }

    /**
     * 设置titles
     */
    public ScrollTab setTitles(List<String> ts) {// 3
        if (this.items != null && ts != null) {
            this.items.clear();
            for (String t : ts) {
                this.items.add(new TabItem(t, ""));
            }
            if (!isFirst) {
                resetTab(0);
                invalidate();
            }
        }

        return this;
    }

    /**
     * 设置titles
     */
    public ScrollTab setTitles(List<String> ts, int position) {// 3
        if (this.items != null && ts != null) {
            this.items.clear();
            for (String t : ts) {
                this.items.add(new TabItem(t, ""));
            }

            this.position = position < items.size() ? position : items.size() - 1;

            if (!isFirst) {
                resetTab(position);
                invalidate();
            }
        }

        return this;
    }

    public ScrollTab setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);

        return this;
    }

    private void resetTab(int position) { //5
        if (items == null || items.size() <= 0 || width <= 0) {
            return;
        }
        isFirst = false;
        count = items.size();
        tabs.clear();
        removeAllViews();
        LinearLayout parent = new LinearLayout(context);
        LayoutParams lp = new LayoutParams(isAvag ? LayoutParams.MATCH_PARENT : LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        parent.setOrientation(LinearLayout.HORIZONTAL);
        parent.setLayoutParams(lp);
        for (int i = 0; i < count; i++) {
            View child = getTabView(i);
            parent.addView(child);
            tabs.add(child);
        }
        addView(parent);

        if (position != 0) {
            setTabPageSelected(position);
        }
    }

    private void setTabPageSelected(int position) {
        onPageSelected(position);
        viewPager.setCurrentItem(position);
    }

    private View getTabView(int i) {
        View child;
        if (type == TYPE_VIEW) {
            child = new TabTextView(context);
            ((TabTextView) child).setTextColor(textColor, textFocusColor);
            ((TabTextView) child).setTextSize(textSize);
        } else {
            child = new TabViewGroup(context);
            ((TabViewGroup) child).setTextColor(textColor, textFocusColor);
            ((TabViewGroup) child).setTextSize(textSize);
        }
        ((TabView) child).setText(items.get(i).title);
        ((TabView) child).setNumber(items.get(i).text, TextUtils.isEmpty(items.get(i).text) ? GONE : VISIBLE);
        if (!isAvag) {
            ((TabView) child).setPadding((int) padding);
        }
        ((TabView) child).notifyData(i == position);
        child.setLayoutParams(new LinearLayout.LayoutParams(isAvag ? width / (count > 0 ? count : 1) : ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        child.setTag(i);
        child.setOnClickListener(this);
        return child;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || count <= 0 || position < 0 || position > count - 1) {
            return;
        }
        if (indicatorType == TYPE_INDICATOR_TREND) {
            float left = tabs.get(position).getLeft() + indicatorPadding;
            float right = tabs.get(position).getRight() - indicatorPadding;
            if (position < count - 1) {
                float nextLeft = tabs.get(position + 1).getLeft() + indicatorPadding;
                float nextRight = tabs.get(position + 1).getRight() - indicatorPadding;
                if (positionOffset < 0.5) {
                    right = right + (nextRight - right) * positionOffset * 2;
                } else {
                    left = left + (nextLeft - left) * (positionOffset - 0.5f) * 2;
                    right = nextRight;
                }
            }
            rectF.set(left, height - indicatorWeight, right, height);
        } else if (indicatorType == TYPE_INDICATOR_TRANSLATION) {
            float left = tabs.get(position).getLeft();
            float right = tabs.get(position).getRight();
            float middle = left + (right - left) / 2;
            if (position < count - 1) {
                float nextLeft = tabs.get(position + 1).getLeft();
                float nextRight = tabs.get(position + 1).getRight();
                float nextMiddle = nextLeft + (nextRight - nextLeft) / 2;
                middle = middle + (nextMiddle - middle) * positionOffset;
            }
            left = middle - indicatorWidth / 2;
            right = middle + indicatorWidth / 2;
            rectF.set(left, height - indicatorWeight, right, height);
        } else {
            float left = tabs.get(position).getLeft();
            float right = tabs.get(position).getRight();
            float middle = left + (right - left) / 2;
            left = middle - indicatorWidth / 2;
            right = middle + indicatorWidth / 2;
            rectF.set(left, height - indicatorWeight, right, height);
        }
        canvas.drawRoundRect(rectF, indicatorRadius, indicatorRadius, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {// 4
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        if (isFirst) {
            resetTab(position);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        if (viewPager == null) {
            position = index;
            positionOffset = 0;
            onChange(index);
            adjustScrollY(index);
            invalidate();
        }
        if (listener != null) {
            listener.onChange(index, v);
        }
    }

    private void onChange(int position) {
        for (int i = 0; i < count; i++) {
            TabView view = (TabView) tabs.get(i);
            view.notifyData(i == position);
        }
    }

    /**
     * 设置红点
     */
    public void setNumber(int position, String text, int visibility) {
        if (position < 0 || position > items.size() - 1) {
            return;
        }
        items.get(position).text = text;
        if (position < 0 || position > count - 1) {
            return;
        }
        TabView view = (TabView) tabs.get(position);
        view.setNumber(text, visibility);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        UILog.d("dsiner_onPageScrolled: position: " + position + " Offset: " + positionOffset);
        if (indicatorType != TYPE_INDICATOR_NONE) {
            this.position = position;
            this.positionOffset = positionOffset;
            invalidate();
        }
    }

    @Override
    public void onPageSelected(int position) {
        UILog.d("dsiner_onPageSelected: position: " + position + " Offset: " + positionOffset);
        onChange(position);

        adjustScrollY(position);

        if (indicatorType == TYPE_INDICATOR_NONE) {
            this.position = position;
            invalidate();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        UILog.d("dsiner_onPageScrollStateChanged: state: " + state);
    }

    private void adjustScrollY(int position) {
        if (isAvag) {
            return;
        }
        View v = tabs.get(position);
        int dr = v.getRight() - (width + getScrollX());
        int dl = getScrollX() - v.getLeft();
        if (dr > 0) {
            smoothScrollBy(dr, 0);
        } else if (dl > 0) {
            smoothScrollBy(-dl, 0);
        }
    }

    public interface OnTabListener {
        void onChange(int position, View v);
    }

    public void setOnTabListener(OnTabListener l) {
        this.listener = l;
    }
}

