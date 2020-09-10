package com.release.simplex.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.release.easybasex.utils.SendThread;
import com.release.simplex.R;

/**
 * @author Mr.release
 * @create 2020/9/10
 * @Describe
 */

public class ThreadActivity extends AppCompatActivity implements SendThread.RunCallback {
    private Button button1 = null;
    private Button button2 = null;
    private Button button3 = null;
    private SendThread sendThread = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("ThreadActivity--->onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        button1 = (Button) findViewById(R.id.btn_1);
        //listener,唤醒线程
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置数据
                sendThread.setData(new byte[]{1,1});
                //唤醒线程
                sendThread.setNotify();
            }
        });

        button2 = (Button) findViewById(R.id.btn_2);
        //listener,进入下一个Activity
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ThreadActivity.this, ThreadOtherActivity.class);
                startActivity(intent);
            }
        });

        button3 = (Button) findViewById(R.id.btn_3);
        //listener,结束线程
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置标志位
                sendThread.setRun(false);
                //设置数据
                sendThread.setData(new byte[]{2,2});
                //唤醒线程
                sendThread.setNotify();
            }
        });
    }

    //项目启动的时候就启动发送线程
    private void startSendThread(){
        sendThread = SendThread.getInstance();
        sendThread.setRunCallback(this);
    }


    @Override
    protected void onResume() {
        System.out.println("ThreadActivity--->onResume()");
        startSendThread();//调用启动线程方法
        super.onResume();
    }


    @Override
    public void runing(byte[] data) {
        System.out.println("发送的数据--runing-->" + data[0] + data[1]);
        System.out.println("currentThread-->" + Thread.currentThread().getName());
    }
}
