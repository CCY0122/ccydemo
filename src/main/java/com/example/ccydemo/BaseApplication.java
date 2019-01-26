package com.example.ccydemo;

import android.app.Application;

import com.example.ccydemo.TestDemo.TestAct;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;


/**
 * Created by ccy(17022) on 2018/10/18 上午9:19
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AutoSizeConfig.getInstance().getUnitsManager().setSupportDP(false).setSupportSP(false).setSupportSubunits(Subunits.MM);
    }
}
