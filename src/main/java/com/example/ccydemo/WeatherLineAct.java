package com.example.ccydemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ccy.miuiweatherline.MiuiWeatherView;
import com.example.ccy.miuiweatherline.WeatherBean;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ccy on 2018-01-26.
 */

public class WeatherLineAct extends BaseActivity {

    private MiuiWeatherView weatherView;
    private List<WeatherBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_act);
        weatherView = (MiuiWeatherView) findViewById(R.id.weather);
        data = new ArrayList<>();
        data.add(new WeatherBean(WeatherBean.CLOUDY,10,"100"));
        weatherView.setData(data);
        Log.d("ccy","1equals ? " + (data == weatherView.getData()));

        Button btn = (Button) findViewById(R.id.b1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ccy","2equals ?  = " +(data == weatherView.getData()));
                makeData(data);
                Log.d("ccy","3equals ?  = " +(data == weatherView.getData()));
                weatherView.notifyDataSetChanged();
                Log.d("ccy","4equals ?  = " +(data == weatherView.getData()));
            }
        });
    }

    private void makeData(List<WeatherBean> data){
        Random random = new Random();
        String[] weathers = WeatherBean.getAllWeathers();
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int a = random.nextInt(6);//随机天气
            int b = 1+random.nextInt(4);//随机连续天气数
            for(int j = 0; j < b; j++){
                count ++;
                int c = random.nextInt(5); //随机温度
                WeatherBean bean = new WeatherBean(weathers[a],20+c,count+":00");
                data.add(bean);
            }
        }
        data.get(2).temperatureStr = "日出";
        data.get(2).time = "测试中文";
        data.get(4).temperatureStr = "日落";
        data.get(4).time = "陈朝勇";

        WeatherBean b1 = new WeatherBean(weathers[0],20,"05:00");
        WeatherBean b2 = new WeatherBean(weathers[1],22,"日出","05:30");
        WeatherBean b3 = new WeatherBean(weathers[2],21,"06:00");
        WeatherBean b4 = new WeatherBean(weathers[2],22,"07:00");
        WeatherBean b5 = new WeatherBean(weathers[2],23,"08:00");
        WeatherBean b6 = new WeatherBean(weathers[3],20,"09:00");
        data.add(b1);
        data.add(b2);
        data.add(b3);
        data.add(b4);
        data.add(b5);
        data.add(b6);
    }
}
