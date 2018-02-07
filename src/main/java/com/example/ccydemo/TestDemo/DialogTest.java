package com.example.ccydemo.TestDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.ccydemo.R;

/**
 * Created by ccy on 2018-02-02.
 */

public class DialogTest extends DialogFragment {

    public DialogTest() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setStyle(STYLE_NO_TITLE,R.style.baseDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_dialog_frag,container,false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
