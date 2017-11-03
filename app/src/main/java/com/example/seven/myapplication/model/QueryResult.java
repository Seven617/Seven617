package com.example.seven.myapplication.model;

import java.util.ArrayList;

/**
 * Created by seven617 on 2017/11/3.
 */

public class QueryResult {
    public String error;
    public ArrayList<Info> results;
    public class Info{
        public String _id;
        public String url;
    }
}
