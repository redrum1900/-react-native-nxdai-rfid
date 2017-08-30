package com.nxd.rfid;

import android.widget.Toast;
import android.content.Context;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class Rfid extends ReactContextBaseJavaModule {

    private Context mContext;

    public Rfid(ReactApplicationContext reactContext) {
        super(reactContext);

        mContext = reactContext;
    }

    @Override
    public String getName() {

        //返回的这个名字是必须的，在rn代码中需要这个名字来调用该类的方法。
        return "RCTRfid";
    }

    @ReactMethod
    public void Toast(String msg){

        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();

    }

    @ReactMethod
    public void getResult(String msg,final Callback callback){
        new Thread(new Runnable(){
            @Override
            public void run(){

                int sss = 2222;
                callback.invoke(sss);
            }
        }).start();
    }
}