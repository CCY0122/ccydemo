package com.example.ccydemo.brvah;

import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.example.ccydemo.R;

import java.util.List;

/**
 * Created by ccy(17022) on 2018/7/6 上午10:39
 */
public class BRVAHAdapter extends BaseSectionQuickAdapter<SectionBean,BRVAHAdapter.TestViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public BRVAHAdapter(List<SectionBean> data) {
        super(R.layout.item_text_img, R.layout.item_text, data);
    }

    @Override
    protected void convertHead(BRVAHAdapter.TestViewHolder helper, SectionBean item) {
        helper.setText(R.id.item_tv,item.header);
    }

    @Override
    protected void convert(BRVAHAdapter.TestViewHolder helper, SectionBean item) {
        helper.setText(R.id.item_tv, item.t.getName());
        helper.setImageResource(R.id.item_img, item.t.getRes());
    }

    class TestViewHolder extends BaseViewHolder {
        public TestViewHolder(View view) {
            super(view);
        }
    }
}
