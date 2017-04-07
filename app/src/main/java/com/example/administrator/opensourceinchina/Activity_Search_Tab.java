package com.example.administrator.opensourceinchina;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.example.administrator.opensourceinchina.fragment.Fragment_LookFor;
import com.example.administrator.opensourceinchina.fragment.Fragment_answer;
import com.example.administrator.opensourceinchina.fragment.Fragment_blog;
import com.example.administrator.opensourceinchina.fragment.Fragment_information;
import com.example.administrator.opensourceinchina.fragment.Fragment_software;

import java.util.ArrayList;
import java.util.List;

public class Activity_Search_Tab extends FragmentActivity{
    private EditText mEdit;
    private TabLayout mTab;
    private ViewPager mView;
    public static String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tab);
         initData();
        initViewPager();
    }

    private void initViewPager() {
        mTab = (TabLayout) findViewById(R.id.search_tab_TabLayout);
        mView = (ViewPager) findViewById(R.id.search_tab_pager);
        List<Fragment> FraList = new ArrayList<>();
        Fragment_software soft = new Fragment_software();
        Fragment_blog blog = new Fragment_blog();
        Fragment_information info = new Fragment_information();
        Fragment_answer answer = new Fragment_answer();
        Fragment_LookFor look = new Fragment_LookFor();
        FraList.add(soft);
        FraList.add(blog);
        FraList.add(info);
        FraList.add(answer);
        FraList.add(look);
        List<String> Strlist = new ArrayList<>();
        Strlist.add("软件");
        Strlist.add("博客");
        Strlist.add("资讯");
        Strlist.add("问答");
        Strlist.add("找人");
        MyFragmentAdapter mAdapter = new MyFragmentAdapter(getSupportFragmentManager(),FraList,Strlist);
        mView.setAdapter(mAdapter);
        mView.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        mTab.setupWithViewPager(mView);

    }

    private void initData() {
        Intent in = getIntent();
        mEdit = (EditText) findViewById(R.id.search_tab_edit);
        mEdit.setText(in.getStringExtra("txt"));
        str = in.getStringExtra("txt");
    }
}
