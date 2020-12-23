package io.dolby.sdk.services;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.voxeet.VoxeetSDK;
import com.voxeet.sdk.json.ParticipantInfo;
import com.voxeet.sdk.services.SessionService;

import io.dolby.sdk.models.ConferenceUserUtil;

public class RNSessionServiceModule extends ReactContextBaseJavaModule {

    private ParticipantInfo _current_user;

    public RNSessionServiceModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNSessionService";
    }

    @ReactMethod
    public void participant(final Promise promise) {
        WritableMap args = _current_user == null ? null : ConferenceUserUtil.toMap(_current_user);
        promise.resolve(args);
    }

    @ReactMethod
    public void open(ReadableMap userInfo, final Promise promise) {
        final ParticipantInfo participantInfo = ConferenceUserUtil.toParticipantInfo(userInfo);

        if (isConnected() && isSameUser(participantInfo)) {
            promise.resolve(true);
            return;
        }

        VoxeetSDK.session()
                .open(participantInfo)
                .then(result -> {
                    _current_user = participantInfo;
                    promise.resolve(result);
                })
                .error(error -> {
                    promise.reject(error);
                });
    }

    @ReactMethod
    public void close(final Promise promise) {
        VoxeetSDK.session()
                .close()
                .then(result -> {
                    _current_user = null;
                    promise.resolve(result);
                })
                .error(error -> {
                    _current_user = null;
                    promise.reject(error);
                });
    }


    private boolean isConnected() {
        SessionService sessionService = VoxeetSDK.session();
        return null != sessionService && sessionService.isSocketOpen();
    }

    private boolean isSameUser(@NonNull ParticipantInfo userInfo) {
        if (userInfo == null || _current_user == null) return false;
        if (userInfo.getExternalId() == null && _current_user.getExternalId() == null) return true;
        return userInfo.getExternalId().equals(_current_user.getExternalId());
    }

}