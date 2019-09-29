package com.example.handlertest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity{
    private TextView statusTextView;
    private Button btnDownload;

    //uiHandler在主线程中创建，所以Handler自动绑定主线程
    private Handler uiHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        statusTextView = (TextView)findViewById(R.id.textView);
        btnDownload = (Button)findViewById(R.id.button_dl);
        System.out.println("Main thread id " + Thread.currentThread().getId());

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DownloadThread downloadThread = new DownloadThread();
                downloadThread.start();
            }
        };

        btnDownload.setOnClickListener(onClickListener);

    }


    class DownloadThread extends Thread{
        @Override
        public void run() {
            try{
                System.out.println("DownloadThread id " + Thread.currentThread().getId());
                System.out.println("开始下载文件");
                //此处让线程DownloadThread休眠5秒中，模拟文件的耗时过程
                Thread.sleep(5000);
                System.out.println("文件下载完成");
                //文件下载完成后更新UI
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Runnable thread id " + Thread.currentThread().getId());
                        MainActivity.this.statusTextView.setText("文件下载完成");
                    }
                };
                uiHandler.post(runnable);//将Runnable对象通过post方法发送到Handler关联的消息队列中，Handler会在合适的时候将让主线程执行Runnable中的代码，从而更新ui
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}


/*
public class MainActivity extends AppCompatActivity{
    private TextView statusTextView;
    private Button btnDownload;
    private static String TAG = "MainActivity";

    // 向Hanlder的构造函数传入一个Handler.Callback对象，并实现Handler.Callback的handleMessage方法

    //也可以不传递callback对象，重写handler的handleMessage方法
    private Handler uihandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //如果使用的是
            Log.i(TAG,"msg.what= "+msg.what);
            switch (msg.what){
                case 1:
                    System.out.println("handleMessage thread id " + Thread.currentThread().getId());
                    System.out.println("msg.arg1:" + msg.arg1);
                    System.out.println("msg.arg2:" + msg.arg2);
                    MainActivity.this.statusTextView.setText("文件下载完成");
                    Log.i(TAG,"##文件下载完成,更新UI ");
                    break;
            }

            System.out.println("handleMessage thread id " + Thread.currentThread().getId());
            MainActivity.this.statusTextView.setText("文件下载完成");

            Log.i(TAG,"文件下载完成,更新UI ");
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        statusTextView = (TextView)findViewById(R.id.textView);
        btnDownload = (Button)findViewById(R.id.button_dl);
        System.out.println("Main thread id " + Thread.currentThread().getId());

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DownloadThread downloadThread = new DownloadThread();
                downloadThread.start();
            }
        };

        btnDownload.setOnClickListener(onClickListener);

        Log.i(TAG,"onCreate ");
    }


    class DownloadThread extends Thread{

        @Override
        public void run() {
            try{
                System.out.println("DownloadThread id " + Thread.currentThread().getId());
                System.out.println("开始下载文件");

                Log.d(TAG,"DownloadThread id " + Thread.currentThread().getId());
                Log.d(TAG,"开始下载文件");
                //此处让线程DownloadThread休眠5秒中，模拟文件的耗时过程
                Thread.sleep(5000);
                Log.d(TAG,"文件下载完成");
                System.out.println("文件下载完成");
                //文件下载完成后更新UI
                //方法一：使用sendMessage进行msg的发送
                Message msg = new Message();//msg对象表示线程要处理的事情

                //方式一：使用obtainMessage方法实例化msg，handleMessage中使用msg.what进行匹配
                msg = uihandler.obtainMessage();
                msg.what = 1;
                msg.arg1 = 123456;
                msg.arg2 = 654321;

                //方式二：Message.obtain实例化msg
                //msg = Message.obtain(uihandler);
                uihandler.sendMessage(msg);
                Log.i(TAG,"sendMessage ");

                //方法二：使用post方法实现runnable接口，然后发送消息
*/
/*                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Runnable thread id " + Thread.currentThread().getId());
                        MainActivity.this.statusTextView.setText("文件下载完成");
                    }
                };
                //将Runnable对象通过post方法发送到Handler关联的消息队列中，Handler会在合适的时候将让主线程执行Runnable中的代码，从而更新ui
                uihandler.post(runnable);*//*

            }catch (InterruptedException e){
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG,"onStart ");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG,"onPause ");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG,"onResume ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart ");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG,"onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG,"onDestroy ");
    }
}
*/


