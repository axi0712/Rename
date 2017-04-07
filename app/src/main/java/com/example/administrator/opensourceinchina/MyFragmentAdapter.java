package com.example.administrator.opensourceinchina;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFralist;
    private List<String> mList;
    public MyFragmentAdapter(FragmentManager fm,List<Fragment> mFralist,List<String> mList) {
        super(fm);
        this.mFralist = mFralist;
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFralist.get(position);
    }

    @Override
    public int getCount() {
        return mFralist.isEmpty()?0:mFralist.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }
}
