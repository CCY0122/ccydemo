package com.example.ccydemo.selectitem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by ccy(17022) on 2018/10/22 下午3:17
 */
public class SelectItemActivity extends BaseActivity {

    @BindView(R.id.s1)
    SelectItem s1;
    @BindView(R.id.s2)
    SelectItem s2;
    @BindView(R.id.s3)
    SelectItem s3;
    @BindView(R.id.s4)
    SelectItem s4;
    @BindView(R.id.s5)
    SelectItem s5;
    @BindView(R.id.s11)
    SelectItem s11;
    private BottomSheetDialog bottomSheetDialog;
    QBadgeView qBadgeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_item_act);
        ButterKnife.bind(this);

        s1.getLeftImgBadge().setBadgeNumber(-1);
        s1.getTitleBadge().setBadgeNumber(-1);
        s1.getRightImgBadge().setBadgeNumber(-1);
        s11.getLeftImgBadge().setBadgeNumber(2);
        new QBadgeView(this).bindTarget(s11).setBadgeNumber(-1).setBadgePadding(4, true).setShowShadow(false);
        s2.getRightImgBadge().setBadgeNumber(-1);
        s3.getRightTextBadge().setBadgeNumber(-1);
        s4.getLeftImgBadge().setBadgeNumber(1);
        qBadgeView = new QBadgeView(this);
        qBadgeView.bindTarget(s4).setBadgeNumber(111);
        s5.setLeftImage(ContextCompat.getDrawable(this, R.drawable.cloudy));
        s5.getSwitchButton().setOnCheckedChangeListener(new CompoundButton
                .OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(SelectItemActivity.this,"asd",Toast.LENGTH_LONG).show();
            }
        });


    }

    @OnClick(R.id.b1)
    void b1() {
       qBadgeView.setBadgeNumber(-1);
    }
}
