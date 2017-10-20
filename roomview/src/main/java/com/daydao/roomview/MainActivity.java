package com.daydao.roomview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoomView roomView = (RoomView) findViewById(R.id.roomView);

        roomView.setLeftbgColor(getResources().getColor(R.color.colorAasdccent));
        roomView.setLeftTextColor(Color.parseColor("#87634F"));
        roomView.setRightbgColor(Color.parseColor("#87634F"));
        roomView.setRightTextColor(Color.WHITE);
        roomView.setRightText("空置");
        roomView.setLeftText("1001111");
        roomView.notifyColor();

        RoomView roomView2 = (RoomView) findViewById(R.id.roomView2);

        roomView2.setLeftbgColor(getResources().getColor(R.color.colorAasdccent));
        roomView2.setLeftTextColor(Color.parseColor("#87634F"));
        roomView2.setRightbgColor(Color.parseColor("#87634F"));
        roomView2.setRightTextColor(Color.WHITE);
        roomView2.setRightText("空置");
        roomView2.setLeftText("10022222222222");
        roomView2.notifyColor();

        RoomView roomView1 = (RoomView) findViewById(R.id.roomView1);

        roomView1.setLeftbgColor(getResources().getColor(R.color.colorAasdccent));
        roomView1.setLeftTextColor(Color.parseColor("#87634F"));
        roomView1.setRightbgColor(Color.parseColor("#87634F"));
        roomView1.setRightTextColor(Color.WHITE);
        roomView1.setRightText("空置");
        roomView1.setLeftText("111111111");
        roomView1.notifyColor();
    }
}
