package io.dolby.sdk.services;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.voxeet.VoxeetSDK;

public class RNCommandServiceModule extends ReactContextBaseJavaModule {

    public RNCommandServiceModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNCommandService";
    }


    @ReactMethod
    public void send(String conferenceId, String message, final Promise promise) {
        VoxeetSDK.command()
                .send(conferenceId, message)
                .then(promise::resolve)
                .error(promise::reject);
    }

}
