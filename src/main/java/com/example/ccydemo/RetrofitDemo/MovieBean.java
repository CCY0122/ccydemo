package com.example.ccydemo.RetrofitDemo;

import java.util.List;

/**
 * Created by ccy on 2017-07-25.
 */

public class MovieBean {
    public int start;
    public int count;
    public int total;
    public String query;
    public String tag;
    public List<Subject> subjects;


    public class Subject{
        public String id;
        public String title;
    }
}
