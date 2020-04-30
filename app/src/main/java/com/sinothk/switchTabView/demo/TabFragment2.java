package com.sinothk.switchTabView.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * TabFragment
 * Created by D on 2017/8/27.
 */
public class TabFragment2 extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_tab, null);
            init();
        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
        return rootView;
    }

    private void init() {
        TextView tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        tvContent.setText(getArguments() != null ? "" + getArguments().getInt("index") : "");
    }
}
