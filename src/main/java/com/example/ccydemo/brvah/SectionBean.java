package com.example.ccydemo.brvah;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by ccy(17022) on 2018/7/6 上午10:40
 */
public class SectionBean extends SectionEntity<Bean> {

    public SectionBean(Bean bean) {
        super(bean);
    }

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }
}
