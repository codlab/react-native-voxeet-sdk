package io.dolby.sdk.services;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.voxeet.VoxeetSDK;

public class RNMediaDeviceServiceModule extends ReactContextBaseJavaModule {

    public RNMediaDeviceServiceModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNMediaDeviceService";
    }

    @ReactMethod
    public void switchCamera(final Promise promise) {
        VoxeetSDK.mediaDevice()
                .switchCamera()
                .then(promise::resolve)
                .error(promise::reject);
    }
}
