package com.example.administrator.opensourceinchina.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrator.opensourceinchina.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_Search extends Activity {
    private EditText mEdit;
    private Button mBtn;
    private MyAdapter mAdapter;
    private ListView mListView;
    private ArrayList<Map<String,String>> mList = new ArrayList<Map<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search);
        initDat();
    }

    private void initDat() {
        mEdit = (EditText) findViewById(R.id.search_edit);
        mListView = (ListView) findViewById(R.id.search_listView);
        mBtn = (Button) findViewById(R.id.search_btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Activity_Search.this,Activity_Search_Tab.class);
                in.putExtra("txt",mEdit.getText().toString());
                startActivity(in);
                getEdit();
                mAdapter = new MyAdapter(mList,Activity_Search.this);
                mListView.setAdapter(mAdapter);
            }
        });
    }

    private void getEdit() {
         for (int i = 0;i<mList.size();i++){
             Map<String,String> map = new HashMap<String,String>();
             map.put("text",mEdit.getText().toString());
             mList.add(map);
         }

    }

}
