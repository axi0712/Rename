package com.example.administrator.opensourceinchina.parsing;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

public interface RetrofitInterface {
//@GET("action/api/news_detail?id=1")
//    Call<ResponseBody> logins(@Query("id") int id);
    @GET("action/api/news_list")
    Call<ResponseBody> login(@Query("catalog") String catalog, @Query("pageIndex")String pageIndex, @Query("pageSize")String pageSize);
//    @GET("action/api/news_list")
//    Call<ResponseBody> login();
}
