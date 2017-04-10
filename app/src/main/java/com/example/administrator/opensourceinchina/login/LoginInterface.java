package com.example.administrator.opensourceinchina.login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/9 0009.
 */

public interface LoginInterface {
    @GET("action/api/login_validate")
    Call<ResponseBody> login(@Query("username")String name,@Query("pwd")String pwd,@Query("keep_login")String keep_login);
}
