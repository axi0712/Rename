package com.example.administrator.opensourceinchina.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.opensourceinchina.R;
import com.thoughtworks.xstream.XStream;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Activity_Login extends Activity implements View.OnClickListener{
    private EditText mLoginEditUsername;
    private EditText mLoginEditPwd;
    private Button mLoginBtnLogin;
    private MyManager mDB;
    private String Name,Pasword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        mDB = new MyManager(Activity_Login.this);
        assignViews();
    }


    private void assignViews() {
        mLoginEditUsername = (EditText) findViewById(R.id.login_edit_username);
        mLoginEditPwd = (EditText) findViewById(R.id.login_edit_pwd);
        mLoginBtnLogin = (Button) findViewById(R.id.login_btn_login);
        mLoginBtnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:
                initViews();
                break;
        }
    }

    private void initViews() {
        String name = mLoginEditUsername.getText().toString().trim();
        String pwd = mLoginEditPwd.getText().toString().trim();
        this.Name=name;
        this.Pasword=pwd;
        if(name.isEmpty()||pwd.isEmpty()){
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
        }else{
            getRetrofit(name,pwd,"1");
        }
    }

    private void getRetrofit(final String username, final String pwd, final String keep_login) {
        Retrofit re = new Retrofit.Builder().baseUrl("http://www.oschina.net/").build();
        LoginInterface login = re.create(LoginInterface.class);
        Call<ResponseBody> ca = login.login(username,pwd,keep_login);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                       InputStream is = response.body().byteStream();
                        XStream xs = new XStream();
                        xs.alias("oschina",LoginBean.class);
                        xs.alias("result",LoginBean.ResultBean.class);
                    LoginBean loginBean = (LoginBean) xs.fromXML(is);
                    Log.i("login",loginBean.getResult().getErrorCode()+loginBean.getResult().getErrorMessage());
                    if(Integer.parseInt(loginBean.getResult().getErrorCode())==1){
                        Toast.makeText(Activity_Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                       Boolean boo =  mDB.insert(Name,Pasword);
                        if(boo){
                            Toast.makeText(Activity_Login.this, "添加成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Activity_Login.this, "添加失败", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Activity_Login.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}
