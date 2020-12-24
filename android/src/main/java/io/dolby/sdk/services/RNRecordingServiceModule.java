package io.dolby.sdk.services;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.voxeet.VoxeetSDK;

public class RNRecordingServiceModule extends ReactContextBaseJavaModule {

    public RNRecordingServiceModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNRecordingService";
    }


    @ReactMethod
    public void start(final Promise promise) {
        VoxeetSDK.recording()
                .start()
                .then(promise::resolve)
                .error(promise::reject);
    }

    @ReactMethod
    public void stop(final Promise promise) {
        VoxeetSDK.recording()
                .stop()
                .then(promise::resolve)
                .error(promise::reject);
    }
}
