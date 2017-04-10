package com.example.administrator.opensourceinchina.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class MyManager {
    private MySql mSql;
    private SQLiteDatabase mDB;
    private final String DB_NAME = "lwx.db";
    private final int DB_VERSION = 1;
    private Context mContext;
    public MyManager(Context mContext){
        this.mContext = mContext;
        mSql = new MySql(mContext,DB_NAME,DB_VERSION);
        mDB = mSql.getWritableDatabase();
    }
    public Boolean insert(String name,String pwd){
         Boolean boo;
        ContentValues con = new ContentValues();
        con.put("name",name);
        con.put("pwd",pwd);
        long insert = mDB.insert("person",null,con);
        if(insert>0){
            boo = true;
        }else{
            boo = false;
        }
        return boo;
    }
}
