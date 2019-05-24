package com.codeayit.mqqtdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MQTTService.IGetMessageCallBack {

    private MyServiceConnection serviceConnection;
    private MQTTService mqttService;

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceConnection = new MyServiceConnection();
        serviceConnection.setIGetMessageCallBack(MainActivity.this);
        textView = findViewById(R.id.tv);

        Intent intent = new Intent(this, MQTTService.class);

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }


    public void publish(View view){
//        MQTTService.publish("topic_in","android mqtt");
        String msg = "{\"sn\":\""+String.valueOf(Build.SERIAL) +"\",\"time\":1558695239194,\"topic\":\"check_online_client\"}";

        MQTTService.publish("check_online_client",msg);
    }

    @Override
    public void setMessage(String message) {

        textView.setText(message);
        mqttService = serviceConnection.getMqttService();
        mqttService.toCreateNotification(message);
        Log.i(MQTTService.TAG, message);

        if (message.contains("check_online_server")){
//        MQTTService.publish("topic_in","android mqtt");
            String msg = "{\"sn\":\""+String.valueOf(Build.SERIAL) +"\",\"time\":1558695239194,\"topic\":\"check_online_client\"}";
            MQTTService.publish("check_online_client",msg);
        }

    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    public  class MyServiceConnection implements ServiceConnection {

        private MQTTService mqttService;
        private MQTTService.IGetMessageCallBack IGetMessageCallBack;

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mqttService = ((MQTTService.CustomBinder)iBinder).getService();
            mqttService.setIGetMessageCallBack(IGetMessageCallBack);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }

        public MQTTService getMqttService(){
            return mqttService;
        }

        public void setIGetMessageCallBack(MQTTService.IGetMessageCallBack IGetMessageCallBack){
            this.IGetMessageCallBack = IGetMessageCallBack;
        }
    }
}
