package com.example.ccydemo.RecyclerViewHeader;

import java.io.File;
import java.util.Date;

/**
 * Created by ccy on 2018-02-22.
 * 基类Item Bean
 */

public class ItemBean {
    public static final int TYPE_NORMAL = 0; //普通item
    public static final int TYPE_HEADER = 1; //时间头item
    private int type = TYPE_NORMAL;
    private int times[]; //图片/视频的修改时间
    private String path; //图片/视频路径

    public ItemBean() {
    }

    public ItemBean(String path) {
        setPath(path);
    }

    public void setPath(String path) {
        this.path = path;
        this.times = getModifyTimes(path);
    }

    public String getPath() {
        return path;
    }

    public int[] getTimes() {
        return times;
    }

    //不应该public，不供外部设置,外部在setPath时便会设置times
    void setTimes(int[] times) {
        this.times = times;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 获取文件修改日期
     */
    public static int[] getModifyTimes(String filePath) {
        File f = new File(filePath);
        long lTime = f.lastModified();
        Date date = new Date(lTime);
        int[] times = new int[6];
        times[0] = date.getYear() + 1900;
        times[1] = date.getMonth() + 1;
        times[2] = date.getDate();
        times[3] = date.getHours();
        times[4] = date.getMinutes();
        times[5] = date.getSeconds();
        return times;
    }

    /**
     * 获取文件修改日期的天数（时间头单位）
     */
    public int getModifyDays() {
        if (times != null) {
            return times[0] * 480 + times[1] * 40 + times[2];
        } else {
            throw new RuntimeException("times为空，请先调用setPath设置图片/视频的路径");
        }
    }

    /**
     * 获取文件修改日期的当天的秒数
     */
    public int getModifySeconds() {
        if (times != null) {
            return times[3] * 3600 + times[4] * 60 + times[5];
        } else {
            throw new RuntimeException("times为空，请先调用setPath设置图片/视频的路径");
        }
    }

    @Override
    public String toString() {
        return "type =  " + type + "path = " + path
                + ";time = " + (times == null ? "null" :
                (times[0] + "-" + times[1] + "-" + times[2] + "-" + times[3] + "-" + times[4] + "-" + times[5]));
    }
}
