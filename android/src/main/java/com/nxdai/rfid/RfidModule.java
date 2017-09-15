package com.nxdai.rfid;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.hyipc.uhf_r2000.hardware.function.UhfRead;
import com.hyipc.uhf_r2000.hardware.function.UhfComm;
import com.hyipc.uhf_r2000.hardware.assist.UhfReadListener;
import com.facebook.react.modules.core.DeviceEventManagerModule;

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
    private void sendEvent(String eventName,Object obj) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, obj);
    }

    @ReactMethod
    public void start(){
        mUhfComm = new UhfComm();
        mUhfComm.init();
        mUhfRead = new UhfRead(new UhfReadListener() {
            @Override
            public void onErrorCaughted(String error) {
            }

            @Override
            public void onContentCaughted(Object[] obj) {
                sendEvent("readContentSuccess", obj);
            }
        });

        mUhfRead.setmMem((byte) 1);
		mUhfRead.setmWordPtr((byte) 0);
		mUhfRead.setmNum((byte) 6);

        mUhfRead.start();
    }

    @ReactMethod
    public void stop(){
        sendEvent("UhfReaderStoped", "stoping...");
    }
}