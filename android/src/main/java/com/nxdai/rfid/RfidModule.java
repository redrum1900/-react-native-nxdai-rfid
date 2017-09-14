package com.nxdai.rfid;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RfidModule extends ReactContextBaseJavaModule {

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
    private void sendEvent(String eventName,String message) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, message);
    }

    @ReactMethod
    public void start(){
        sendEvent("UhfReaderStarted", "reading...");
    }

    @ReactMethod
    public void stop(){
        sendEvent("UhfReaderStoped", "stoping...");
    }
}