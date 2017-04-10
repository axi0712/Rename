package com.example.administrator.opensourceinchina.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.adapter.ViewHolder;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.opensourceinchina.R;
import com.example.administrator.opensourceinchina.search.Activity_Search_Tab;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class Fragment_answer extends Fragment {

    private View v;
    private PullToRefreshRecyclerView mView;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private ArrayList<SoftWare> mList = new ArrayList<>();
    private MyAdapter mAdapter;
    private int pageIndex = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_software, null);

        initData();
        return v;
    }

    private void initData() {
        mView = (PullToRefreshRecyclerView) v.findViewById(R.id.fra_soft_recyclerview);
        mShared = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        mEditor = mShared.edit();
//        pageIndex = mShared.getInt("Index", 1);
        Log.e("KanKanpost",pageIndex+"");
        if(Activity_Search_Tab.str!=null){
            getRetro("post",Activity_Search_Tab.str,String.valueOf(pageIndex), "10");
        }

//        pageIndex++;
//        mEditor.putInt("Index", pageIndex);
//        mEditor.commit();
        LinearLayoutManager layout = new LinearLayoutManager(getActivity().getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);

        mView.setLayoutManager(layout);
        mView.setPullRefreshEnabled(true);//下拉刷新
        mView.setLoadingMoreEnabled(true);//上拉加载
        mView.displayLastRefreshTime(true);//显示上次刷新的时间
        //设置刷新回调
        mView.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                mView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.setRefreshComplete();
                        mList.clear();
                        for(int i = 1;i<=pageIndex;i++){
                            if (Activity_Search_Tab.str != null) {
                                getRetro("post", Activity_Search_Tab.str, String.valueOf(i), "10");
                            }
                        }


//                        mEditor.putInt("Index",pageIndex);
//                        mEditor.commit();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                mView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndex++;
                        mView.setLoadMoreComplete();
                        if (Activity_Search_Tab.str != null) {
                            getRetro("post", Activity_Search_Tab.str, String.valueOf(pageIndex), "10");
                        }

                        mEditor.putInt("Index",pageIndex);
                        mEditor.commit();
                    }
                }, 2000);
            }
        });
    }

    private void getRetro(String catalog, String content, String PageIndex, String pageSize) {
        Retrofit re = new Retrofit.Builder().baseUrl("http://www.oschina.net/").build();
        FragmentInterface in = re.create(FragmentInterface.class);
        Call<ResponseBody> call = in.login(catalog, content, PageIndex, pageSize);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String str = response.body().string();
                        Log.e("Strpost",str+"");
                        StringReader reader = new StringReader(str);
                        InputSource is = new InputSource(reader);
                        SAXParserFactory sacF = SAXParserFactory.newInstance();
                        SAXParser par = sacF.newSAXParser();
                        XMLReader rea = par.getXMLReader();
                        MySoftHandler han = new MySoftHandler();
                        rea.setContentHandler(han);
                        rea.parse(is);
                        ArrayList<SoftWare> list = han.getList();
                        if (mList != null) {
                            mList.addAll(list);
                            Log.i("successpost", list+"hheheda");
                            mAdapter = new MyAdapter(getActivity().getApplicationContext(), mList);
                            mView.setAdapter(mAdapter);

                            Log.e("countpost",mAdapter.getItemCount()+"条");
                        }
//                        if (mAdapter != null) {
//                            mAdapter.notifyDataSetChanged();
//                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private class MySoftHandler extends DefaultHandler {
        private String mNameStr;
        private ArrayList<SoftWare> mList = new ArrayList<>();
        private SoftWare mSoft;

        private ArrayList<SoftWare> getList() {
            return mList;
        }
        //天假只是
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            mNameStr = qName;
            if (mNameStr.equals("result")) {
                if (mSoft == null) {
                    mSoft = new SoftWare();
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (qName.equals("result")) {
                mList.add(mSoft);
                mSoft = null;
            }
            mNameStr = "";
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            String str = new String(ch, start, length);
            if (mNameStr.equals("objid")) {
                mSoft.setObjid(str);
            } else if (mNameStr.equals("type")) {
                mSoft.setType(str);
            } else if (mNameStr.equals("title")) {
                mSoft.setTitle(str);
            } else if (mNameStr.equals("description")) {
                mSoft.setDescription(str);
            } else if (mNameStr.equals("url")) {
                mSoft.setUrl(str);
            }
        }
    }

    class MyAdapter extends com.androidkun.adapter.BaseAdapter<SoftWare> {


        public MyAdapter(Context context, List<SoftWare> datas) {
            super(context, R.layout.fragment_soft_item, datas);
        }

        @Override
        public void convert(ViewHolder holder, SoftWare softWare) {
//            holder.setText(R.id.fra_soft_item_objid, softWare.getObjid() + "");
//            holder.setText(R.id.fra_soft_item_type, softWare.getType() + "");
            holder.setText(R.id.fra_soft_item_title, softWare.getTitle() + "");
            holder.setText(R.id.fra_soft_item_description, softWare.getDescription() + "");
            holder.setText(R.id.fra_soft_item_url, softWare.getUrl() + "");
        }
    }
}
