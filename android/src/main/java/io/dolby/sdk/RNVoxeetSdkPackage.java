package io.dolby.sdk;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.dolby.sdk.services.RNCommandServiceModule;
import io.dolby.sdk.services.RNConferenceServiceModule;
import io.dolby.sdk.services.RNMediaDeviceServiceModule;
import io.dolby.sdk.services.RNRecordingServiceModule;
import io.dolby.sdk.services.RNSessionServiceModule;
import io.dolby.sdk.video.RNVoxeetSDKVideoViewManager;

public class RNVoxeetSdkPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.asList(
                new RNVoxeetSdkModule(reactContext),
                new RNCommandServiceModule(reactContext),
                new RNConferenceServiceModule(reactContext),
                new RNMediaDeviceServiceModule(reactContext),
                new RNRecordingServiceModule(reactContext),
                new RNSessionServiceModule(reactContext)
        );
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.singletonList(new RNVoxeetSDKVideoViewManager());
    }
}