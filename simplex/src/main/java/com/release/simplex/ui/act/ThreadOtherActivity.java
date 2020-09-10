package com.release.simplex.ui.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.release.easybasex.utils.SendThread;
import com.release.simplex.R;

/**
 * @author Mr.release
 * @create 2020/9/9
 * @Describe
 */

public class ThreadOtherActivity extends AppCompatActivity {
    private Button button4 = null;
    private Button button5 = null;
    private SendThread sendThread = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("OtherActivity--->onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_other);
        button4 = (Button) findViewById(R.id.btn_4);
        //listener
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置数据
                sendThread.setData(new byte[]{4, 4});
                //唤醒线程
                sendThread.setNotify();
            }
        });
        button5 = (Button) findViewById(R.id.btn_5);
        //listener  结束线程
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置标志位
                sendThread.setRun(false);
                //设置数据
                sendThread.setData(new byte[]{5, 5});
                //唤醒线程
                sendThread.setNotify();
            }
        });
    }

    //得到发送线程
    private void getSendThread() {
        sendThread = SendThread.getInstance();
    }



    @Override
    protected void onResume() {
        System.out.println("OtherActivity--->onResume()");
        getSendThread();//得到发送线程
        super.onResume();
    }

}
