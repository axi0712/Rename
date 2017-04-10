package com.example.administrator.opensourceinchina.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.opensourceinchina.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class MyAdapter extends BaseAdapter {
    private ArrayList<Map<String,String>> mList ;
    private Context mContext;

    public MyAdapter(ArrayList<Map<String, String>> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.isEmpty()?0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder ho = null;
        if(convertView == null){
            ho = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_search_item,null);
            ho.mText = (TextView) convertView.findViewById(R.id.search_item_text);
            convertView.setTag(ho);
        }else{
            ho = (Holder) convertView.getTag();
        }
        Map<String,String> map = mList.get(position);
        ho.mText.setText(map.get("text"));
        return convertView;
    }
    class Holder{
        private TextView mText;
    }
}
