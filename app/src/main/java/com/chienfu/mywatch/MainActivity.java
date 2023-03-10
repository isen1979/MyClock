package com.chienfu.mywatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 獲取自定義View實例
        ClockView clockView = (ClockView) findViewById(R.id.clock_view);
        // 設置相關參數（如果有）
    }
}
