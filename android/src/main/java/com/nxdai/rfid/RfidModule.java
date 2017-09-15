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

    private UhfRead mUhfRead;
    private UhfComm mUhfComm;
    private final ReactApplicationContext reactContext;

    public RfidModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
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

    // @ReactMethod
    // public void start() {
    //     WritableMap map = Arguments.createMap();
    //     WritableArray results = Arguments.createArray();
    //     results.pushString("3");
    //     results.pushString("4");
    //     map.putArray("results", results);
    //     sendEvent("readContentsSuccess", map);
    // }

   @ReactMethod
   public void start(){
       mUhfComm = new UhfComm();
       mUhfComm.init();
       mUhfRead = new UhfRead(new UhfReadListener() {
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

       mUhfRead.setmMem((byte) 1);
       mUhfRead.setmWordPtr((byte) 0);
       mUhfRead.setmNum((byte) 6);
       mUhfRead.start();
   }

    @ReactMethod
    public void stop(){
        sendEventWithString("UhfReaderStoped", "stoping...");
    }
}