package com.example.administrator.opensourceinchina.fragment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public interface  FragmentInterface {
    @GET("action/api/search_list")
    Call<ResponseBody> login(@Query("catalog") String catalog,@Query("content") String content,@Query("pageIndex") String pageIndex,@Query("pageSize")String pageSize);

}
