package com.example.ccydemo.RecyclerViewHeader;

/**
 * Created by ccy on 2018-02-24.
 * 显示图片时需要的一些信息
 */

public class MediaItemBean extends ItemBean {

    public MediaItemBean(String path){
        super(path);
    }

    public boolean hasFishEyeFrame = false; //有鱼眼信息帧

    public boolean canPlay = false; //可播放的（是视频或鱼眼图片）

}
