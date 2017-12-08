package com.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.myapplication.R;
import com.myapplication.bean.ProportionViewBean;
import com.myapplication.view.ProportionView1;
import com.myapplication.view.ProportionView2;
import com.myapplication.view.ProportionView3;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * 作者: Nathans'Liu
 * 邮箱: a1053128464@qq.com
 * 时间: 2017/11/27 14:34
 * 描述:
 */

public class ProportionViewAdapter extends CommonAdapter<ProportionViewBean> {
    private ProportionView1 view;
    public ProportionViewAdapter(Context context, List<ProportionViewBean> datas) {
        super(context, R.layout.item_main, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final ProportionViewBean ProportionViewBean, int position) {
        view=  holder.getView(R.id.pv);
        view.leftTextView(ProportionViewBean.getTitle());
        view.proportionTextView(ProportionViewBean.getD());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("item",ProportionViewBean.getTitle());
            }
        });
    }
}
