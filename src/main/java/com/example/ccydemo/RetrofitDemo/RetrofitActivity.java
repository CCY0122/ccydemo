package com.example.ccydemo.RetrofitDemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ccy on 2017-07-25.
 */

public class RetrofitActivity extends BaseActivity {

    @BindView(R.id.b1) Button b1;
    @BindView(R.id.b2) Button b2;
    @BindView(R.id.b3) Button b3;
    @BindView(R.id.b4) Button b4;
    @BindView(R.id.b5) Button b5;
    @BindView(R.id.response) TextView responseTv;
    @BindView(R.id.response_bean) TextView text;
    @BindView(R.id.pb) ProgressBar pb;
    @BindView(R.id.e1) EditText e1;
    @BindView(R.id.e2) EditText e2;

    private static String BASE_URL = "https://api.douban.com/"; //  末尾带/
    private Retrofit retrofit;
    private MovieServiceApi movieServiceApi;
    private OkHttpClient client;
    private StringBuilder responseSb = new StringBuilder();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_act);
        ButterKnife.bind(this);

        initLogOkHttp();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        movieServiceApi= retrofit.create(MovieServiceApi.class);

    }

    private void initLogOkHttp() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
//                Log.d("retrofit","retrofit,okhttp Interceptor log = " +message);
                responseSb.append(message+"\n");
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .build();
    }

    @OnClick(R.id.b1)
    public void onb1(Button b){
        Call<MovieBean> call = movieServiceApi.searchMovie(e1.getText()+"");
        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                pb.setVisibility(View.GONE);
                String url = call.request().url().url().toString();
                int code = response.code();
                String message = response.message();
                Log.d("retrofit","url = "+ url+";code = " + code+";meaasge = " + message+";toString = " + response.toString());
                setText(response.body());
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                pb.setVisibility(View.GONE);
                text.setText("failed");
            }
        });

    }

    private void setText(MovieBean bean) {
        StringBuilder sb = new StringBuilder();
        if(bean == null){
            sb.append("查询错误");
        }else if(bean.subjects.size() == 0){
            sb.append("查询结果为空");
        } else{
            for (MovieBean.Subject sub :bean.subjects){
                sb.append(sub.title+" ; ");
            }
        }
        text.setText(sb.toString());
        responseTv.setText("--"+responseSb);
        responseSb.delete(0,responseSb.length());
    }

    @OnClick(R.id.b2)
    public void onb2(Button b){
        pb.setVisibility(View.VISIBLE);
        Call<MovieBean> call = movieServiceApi.searchMovie1("search",e1.getText()+"",e2.getText()+"");
        call.enqueue(new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                pb.setVisibility(View.GONE);
                setText(response.body());
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                pb.setVisibility(View.GONE);
                text.setText("failed");
            }
        });
    }
    @OnClick(R.id.b3)
    public void onb3(Button b){
        pb.setVisibility(View.VISIBLE);
        Call<MovieBean> call = movieServiceApi.searchMovie2();
        call.enqueue(new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                pb.setVisibility(View.GONE);
                setText(response.body());
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                pb.setVisibility(View.GONE);
                text.setText("failed");
            }
        });
    }


    @OnClick(R.id.b4)
    public void onb4(Button b){
        pb.setVisibility(View.VISIBLE);
        Call<MovieBean> call = movieServiceApi.searchMovie3("喜剧");
       call.enqueue(new Callback<MovieBean>() {
           @Override
           public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
               pb.setVisibility(View.GONE);
               setText(response.body());
           }

           @Override
           public void onFailure(Call<MovieBean> call, Throwable t) {
               pb.setVisibility(View.GONE);
               text.setText("failed");
           }
       });
    }





    interface MovieServiceApi{
        //单参数
        @GET("/v2/movie/search")
        Call<MovieBean> searchMovie(@Query("q") String queryStr);

        //带path，多参数
        @GET("/v2/movie/{search}")
        Call<MovieBean> searchMovie1(@Path("search") String search,@Query("q") String queryStr,@Query("tag") String tag);

        //固定参数
        @GET("/v2/movie/search?q=张艺谋&tag=喜剧")
        Call<MovieBean> searchMovie2();

        //固定参数动态参数混用
        @GET("/v2/movie/search?q=张艺谋")
        Call<MovieBean> searchMovie3(@Query("tag") String tag);
    }
}
