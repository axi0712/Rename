package com.example.administrator.opensourceinchina.parsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.opensourceinchina.R;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Activity_WebView extends Activity {
    private WebView mWeb;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__web_view);
        mWeb = (WebView) findViewById(R.id.webView_web);
        Intent in = getIntent();
       String id=in.getStringExtra("webView");
        getInfo(id);
        mWeb.getSettings().setJavaScriptEnabled(true);
        mWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(url);
                return  true;
            }
        });
    }
    private  void getInfo(String id){
        Retrofit  retrofit=new Retrofit.Builder().baseUrl("http://www.oschina.net/").build();
        Retrofit_ retrofit_ = retrofit.create(Retrofit_.class);
        Call<ResponseBody> login = retrofit_.login(id);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        SAXParserFactory  saf=SAXParserFactory.newInstance();
                        try {
                            try {
                                String str=response.body().string();
                                StringReader  reader=new StringReader(str);
                                InputSource  source=new InputSource(reader);
                                SAXParser saxParser=saf.newSAXParser();
                                XMLReader xmlReader=saxParser.getXMLReader();
                                DefaultTwo   defaultTwo=new DefaultTwo();
                                xmlReader.setContentHandler(defaultTwo);
                                try {
                                    xmlReader.parse(source);
                                    String string = defaultTwo.getString();
                                    mWeb.loadUrl(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
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
    private  interface Retrofit_{
        @GET("action/api/news_detail")
        Call<ResponseBody> login(@Query("id") String id);
    }
}
