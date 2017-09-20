package com.nxdai.rfid;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.hyipc.uhf_r2000.hardware.function.UhfComm;
import com.hyipc.uhf_r2000.hardware.function.UhfRead;
import com.hyipc.uhf_r2000.hardware.assist.UhfReadListener;

public class RfidModule extends ReactContextBaseJavaModule {

    private final UhfRead uhfRead;
    private final UhfComm uhfComm;
    private final ReactApplicationContext reactContext;
    private boolean first = true;

    public RfidModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.uhfComm = new UhfComm();
        uhfComm.init();
        this.uhfRead = new UhfRead(new UhfReadListener() {
            @Override
            public void onErrorCaughted(String error) {
            }

            @Override
            public void onContentCaughted(Object[] contents) {
                WritableMap map = Arguments.createMap();
                WritableArray results = Arguments.createArray();
                for (int i = 0; i < contents.length; i++) {
                    String content = (String) contents[i];
                    results.pushString(content);
                }
                map.putArray("results", results);
                sendEvent("readContentsSuccess", map);
            }
        });
        uhfRead.setmMem((byte) 1);
        uhfRead.setmWordPtr((byte) 0);
        uhfRead.setmNum((byte) 6);
    }

    @Override
    public String getName() {

        //返回的这个名字是必须的，在rn代码中需要这个名字来调用该类的方法。
        return "Rfid";
    }

    // Send event to JS
    private void sendEvent(String eventName, WritableMap params) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
    }

    private void sendEventWithString(String eventName, String message) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, message);
    }

    @ReactMethod
    public void start() {
        uhfRead.count = 10;
        if(first){
            uhfRead.start();
            first = false;
        }else{
            uhfRead.reStart();
        }
    }

    @ReactMethod
    public void stop(){
        uhfRead.pause();
    }

    @ReactMethod
    public void restart(){
        uhfRead.reStart();
    }
}