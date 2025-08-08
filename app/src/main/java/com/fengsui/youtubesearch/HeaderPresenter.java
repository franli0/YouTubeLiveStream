package com.fengsui.youtubesearch;


import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.RowHeaderView;

public class HeaderPresenter extends RowHeaderPresenter {

    private Typeface fontType;

    public HeaderPresenter(Typeface fontType){
        this.fontType = fontType;
    }

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        Presenter.ViewHolder viewHolder =  super.onCreateViewHolder(parent);

        View rooView = viewHolder.view;
        rooView.setFocusable(true);
        rooView.setFocusableInTouchMode(true);
        RowHeaderView mTitleView = rooView.findViewById(R.id.row_header);
        TextView TextView = rooView.findViewById(R.id.row_header_description);
        mTitleView.setTypeface(fontType);
        TextView.setTypeface(fontType);
        return viewHolder;
    }

    @Override
    protected void onSelectLevelChanged(ViewHolder holder) {
        super.onSelectLevelChanged(holder);
        View rooView = holder.view;
        RowHeaderView mTitleView = rooView.findViewById(R.id.row_header);
        TextView description = rooView.findViewById(R.id.row_header_description);
        if(rooView.getAlpha() == 1f){
            mTitleView.setTextColor(Color.RED);
            description.setTextColor(Color.argb(200,255,0,0));
        }else{
            mTitleView.setTextColor(Color.WHITE);
            description.setTextColor(Color.WHITE);
        }
    }
}
