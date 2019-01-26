package com.example.ccydemo.matisse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.security.Permission;
import java.util.List;

/**
 * Created by ccy(17022) on 2018/7/18 上午9:24
 */
public class MatisseTestAct extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matisse_act);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MatisseTestAct.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){

                    if(ActivityCompat.shouldShowRequestPermissionRationale(MatisseTestAct.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        Toast.makeText(MatisseTestAct.this,"再次申请，提示申请原因",Toast.LENGTH_LONG).show();
                    }
                    ActivityCompat.requestPermissions(MatisseTestAct.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                }else {
                    Matisse.from(MatisseTestAct.this).choose(MimeType.ofAll())
                            .countable(true)
                            .imageEngine(new GlideEngine())
                            .maxSelectable(9)
                            .forResult(1);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Matisse.from(MatisseTestAct.this).choose(MimeType.ofAll())
                        .countable(true)
                        .imageEngine(new GlideEngine())
                        .forResult(1);
            }else {
                Toast.makeText(MatisseTestAct.this,"用户拒绝权限",Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            List<Uri> uris = Matisse.obtainResult(data);
            for (int i = 0; i < uris.size(); i++) {
                Log.d("ccy","i = " + i + ";uri = " + uris.get(i).toString());
            }
            List<String> paths = Matisse.obtainPathResult(data);
            for (int i = 0; i < paths.size(); i++) {
                Log.d("ccy","i = " + ";path = " + paths.get(i));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
