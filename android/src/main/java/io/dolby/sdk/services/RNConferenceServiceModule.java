package io.dolby.sdk.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.voxeet.VoxeetSDK;
import com.voxeet.sdk.json.internal.MetadataHolder;
import com.voxeet.sdk.json.internal.ParamsHolder;
import com.voxeet.sdk.models.Conference;
import com.voxeet.sdk.services.builders.ConferenceCreateOptions;

import io.dolby.sdk.models.ConferenceUtil;

public class RNConferenceServiceModule extends ReactContextBaseJavaModule {

    private final static String TAG = RNConferenceServiceModule.class.getSimpleName();

    public RNConferenceServiceModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNConferenceService";
    }


    @ReactMethod
    public void create(@Nullable ReadableMap options, @NonNull final Promise promise) {
        String conferenceId = null;
        MetadataHolder metadataHolder = new MetadataHolder();
        ParamsHolder paramsHolder = new ParamsHolder();

        if (options != null) {
            if (options.hasKey("alias")) {
                conferenceId = options.getString("alias");
            }

            if (options.hasKey("params")) {
                ReadableMap params = options.getMap("params");

                if (params != null) {
                    if (valid(params, "dolbyVoice"))
                        paramsHolder.putValue("dolbyVoice", getBoolean(params, "dolbyVoice"));

                    if (valid(params, "ttl"))
                        paramsHolder.putValue("ttl", getInteger(params, "ttl"));

                    if (valid(params, "rtcpMode"))
                        paramsHolder.putValue("rtcpMode", getString(params, "rtcpMode"));

                    if (valid(params, "videoCodec"))
                        paramsHolder.setVideoCodec(getString(params, "videoCodec"));

                    if (valid(params, "liveRecording"))
                        paramsHolder.putValue("liveRecording", getBoolean(params, "liveRecording"));
                }
            }
        }

        VoxeetSDK.conference()
                .create(new ConferenceCreateOptions.Builder()
                        .setConferenceAlias(conferenceId)
                        .setMetadataHolder(metadataHolder)
                        .setParamsHolder(paramsHolder).build()
                )
                .then(result -> {
                    promise.resolve(ConferenceUtil.toMap(result));
                })
                .error(promise::reject);
    }

    @ReactMethod
    public void join(String conferenceId, @Nullable ReadableMap map, final Promise promise) {
        Conference expectedConference = VoxeetSDK.conference().getConference(conferenceId);
        if (expectedConference == null) {
            promise.reject("-1", "Invalid conference, check the conferenceId used");
            return;
        }

        boolean listener = false;
        if (map != null && valid(map, "user")) {
            ReadableMap user = getMap(map, "user");
            listener = user != null && "listener".equals(getString(user, "type"));
        }

        // TODO when the SDK is using join parameters, use them
        if (listener) {
            Log.d(TAG, "Joining as a listener");
            VoxeetSDK.conference()
                    .listen(expectedConference)
                    .then(conference -> {
                        promise.resolve(ConferenceUtil.toMap(conference));
                    })
                    .error(promise::reject);
        } else {
            Log.d(TAG, "Joining as a user");
            VoxeetSDK.conference()
                    .join(expectedConference)
                    .then(conference -> {
                        promise.resolve(ConferenceUtil.toMap(conference));
                    })
                    .error(promise::reject);
        }
    }

    @ReactMethod
    public void leave(final Promise promise) {
        VoxeetSDK.conference()
                .leave()
                .then(promise::resolve)
                .error(promise::reject);
    }

    @ReactMethod
    public void startVideo(final Promise promise) {
        VoxeetSDK.conference()
                .startVideo()
                .then(promise::resolve)
                .error(promise::reject);
    }

    @ReactMethod
    public void stopVideo(final Promise promise) {
        VoxeetSDK.conference()
                .stopVideo()
                .then(promise::resolve)
                .error(promise::reject);
    }

    private int getInteger(@NonNull ReadableMap map, @NonNull String key) {
        try {
            return map.hasKey(key) ? map.getInt(key) : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean getBoolean(@NonNull ReadableMap map, @NonNull String key) {
        try {
            return map.hasKey(key) && map.getBoolean(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Nullable
    private ReadableMap getMap(@NonNull ReadableMap map, @NonNull String key) {
        try {
            return map.hasKey(key) ? map.getMap(key) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    private String getString(@NonNull ReadableMap map, @NonNull String key) {
        try {
            if (map.hasKey(key)) return map.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean valid(@NonNull ReadableMap map, @NonNull String key) {
        try {
            return map.hasKey(key) && !map.isNull(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}