package com.example.administrator.opensourceinchina.parsing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.opensourceinchina.login.Activity_Login;
import com.example.administrator.opensourceinchina.search.Activity_Search;
import com.example.administrator.opensourceinchina.R;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

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

public class MainActivity extends Activity {
    private EditText mEdit;
    private PullToRefreshRecyclerView mView;
    private List<Details> mList = new ArrayList<>();
    private MyAdapter mAdapter;
   private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private int pageIndex;
    private TextView mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = (PullToRefreshRecyclerView) findViewById(R.id.main_recyclerview);
        mShared = getSharedPreferences("data",MODE_PRIVATE);
        mEditor = mShared.edit();
//        pageIndex = mShared.getInt("Index",1);
        getCon("1",String.valueOf(pageIndex),"10");
//        pageIndex++;
//        mEditor.putInt("Index",pageIndex);
//        mEditor.commit();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mView.setLayoutManager(layoutManager);
        //是否开启下拉刷新功能
        mView.setPullRefreshEnabled(true);
        //是否开启上拉加载功能
        mView.setLoadingMoreEnabled(true);
        //设置是否显示上次刷新的时间
        mView.displayLastRefreshTime(true);
        //设置刷新回调
        mView.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                mView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      mView.setRefreshComplete();
                        //模拟没有数据的情况
                        mList.clear();
//                        pageIndex++;
//                        mEditor.putInt("Index",pageIndex);
//                        mEditor.commit();
                        for(int i = 1;i<=pageIndex;i++){
                            getCon("1",String.valueOf(pageIndex),"10");

                        }
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                mView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndex++;
                        mView.setLoadMoreComplete();
                        getCon("1",String.valueOf(pageIndex),"5");
                        mEditor.putInt("Index",pageIndex);
                        mEditor.commit();
                    }
                },2000);

            }
        });
        //主动触发下拉刷新操作
//       mView.onRefresh();
      mEdit = (EditText) findViewById(R.id.main_edit);
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Activity_Search.class);
                startActivity(in);
            }
        });
        mLogin = (TextView) findViewById(R.id.main_text_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Activity_Login.class);
                startActivity(in);
            }
        });
    }

    private void getCon(String catalog, String pageIndex, String pageSize) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL("http://www.oschina.net/action/api/news_detail");
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setDoOutput(true);
//                    con.setDoInput(true);
//                    con.connect();
//                    OutputStream os = con.getOutputStream();
//                    String str = "id=1";
//                    os.write(str.getBytes());
//                    os.flush();
//                    os.close();
//                    InputStream in = con.getInputStream();
//                    InputSource is = new InputSource(in);
//                    SAXParserFactory spf = SAXParserFactory.newInstance();
//                    SAXParser sp = spf.newSAXParser();
//                    XMLReader reader = sp.getXMLReader();
//                    MyHandler mHand = new MyHandler();
//                    reader.setContentHandler(mHand);
//                    reader.parse(is);
//
//                   ArrayList<Details> mList = mHand.getmList();
//                    MyAdapter mAdapter = new MyAdapter(MainActivity.this,mList);
//                    mView.setAdapter(mAdapter);
//                    mView.setPullRefreshEnabled(true);//是否开启下拉刷新功能
//                    mView.setLoadingMoreEnabled(true);//是否开启上拉加载功能
//                    mView.displayLastRefreshTime(true);//设置是否显示上次刷新的时间
//                    mView.onRefresh();//主动触发下拉刷新操作
//// for(Details dd:mList){
////                        mList.add(dd);
////                        Log.i("success",dd.toString());
////                    }
////                      mView.setAdapter(mAdapter = new HomeAdapter(mList));
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ParserConfigurationException e) {
//                    e.printStackTrace();
//                } catch (SAXException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.oschina.net/").build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<ResponseBody> login = retrofitInterface.login(catalog, pageIndex, pageSize);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String str = response.body().string();
                        StringReader reader = new StringReader(str);
                        InputSource source = new InputSource(reader);
                        //先构建sax解析器工厂实例
                        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                        try {
                            //通过解析器工厂获取解析器对象
                            SAXParser saxParser = saxParserFactory.newSAXParser();
                            //获取读取事件源实例
                            XMLReader xmlReader = saxParser.getXMLReader();
                            //实例化时间处理器对象
                            MyHandler defaultDemo = new MyHandler();
                            //将事件处理器设置给事件源
                            xmlReader.setContentHandler(defaultDemo);
                            //录入数据  就是指向那个文件
                            xmlReader.parse(source);
                            ArrayList<Details> list = defaultDemo.getmList();
                            if (mList != null) {
                               mList.addAll(list);
                                Log.i("success",mList.toString());
                                mAdapter = new MyAdapter(MainActivity.this, mList);
                                mView.setAdapter(mAdapter);
                            }

//                            if (mAdapter != null) {
//                                mAdapter.notifyDataSetChanged();
//                            }

                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                   t.printStackTrace();
            }
        });
    }


    class MyAdapter extends BaseAdapter<Details> {


        public MyAdapter(Context context, List<Details> datas) {
            super(context, R.layout.activity_recycle_item, datas);
        }

        @Override
        public void convert(ViewHolder holder, final Details details) {
            holder.setText(R.id.id_num_one, details.getTitle() + "");
//            holder.setText(R.id.id_num_two, details.getUrl()+"");
            holder.setText(R.id.id_num_three, details.getBody() + "");
            holder.setOnclickListener(R.id.item, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(MainActivity.this,Activity_WebView.class);
                    in.putExtra("webView",details.getId());
                    startActivity(in);
                }
            });
        }
    }
//    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
//        private ArrayList<Details> mList;
//
//        public HomeAdapter(ArrayList<Details> mList) {
//            this.mList = mList;
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_recycle_item,parent,false));
//
//            return holder;
//        }
//
//        @Override
//        public int getItemCount() {
//            return mList.isEmpty()?0:mList.size();
//        }
//
//        @Override
//        public void onBindViewHolder(MyViewHolder holder, int position) {
//                     Details de = mList.get(position);
//                     holder.title.setText(de.getTitle()+"");
//            holder.url.setText(de.getUrl()+"");
//            holder.body.setText(de.getBody()+"");
//        }
//
//        class MyViewHolder extends RecyclerView.ViewHolder{
//            TextView title,url,body;
//            public MyViewHolder(View itemView) {
//                super(itemView);
//               title = (TextView) itemView.findViewById(R.id.id_num_one);
//                url = (TextView) itemView.findViewById(R.id.id_num_two);
//                body = (TextView) itemView.findViewById(R.id.id_num_three);
//
//            }
//        }
//    }


}
