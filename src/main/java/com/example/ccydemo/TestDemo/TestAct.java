package com.example.ccydemo.TestDemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy on 2018-02-02.
 */

public class TestAct extends BaseActivity {

    //TODO:1、如何保持dialogfragment悬浮感 对应哪个style？2、bottomSheet 3、其他地方使用Menu xml 创建菜单方法 4、改造现在UI库里的NumberPicker

    DialogTest d;
    String[] s = {"1","2"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_act);
        ButterKnife.bind(this);
        d = new DialogTest();
    }

    @OnClick(R.id.b1)
    void openDialog(View v){
        d.show(getSupportFragmentManager(),"1");
//        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.baseDialog);
//        builder.setView(R.layout.test_dialog_frag);
//        builder.show();
//        PopupMenu popupMenu = new PopupMenu(this,v);
//        popupMenu.getMenuInflater().inflate(R.menu.test_menu,popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(TestAct.this,""+item.getTitle(),Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//        popupMenu.show();
    }


}
