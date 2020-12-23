package io.dolby.sdk.specifics.waiting;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;

import io.dolby.sdk.services.RNConferenceServiceModule;

public class WaitingJoinHolder extends WaitingAbstractHolder {
    private ReadableMap map;
    private String conferenceId;

    public WaitingJoinHolder(RNConferenceServiceModule module, String conferenceId, ReadableMap map, Promise promise) {
        super(module, promise);
        this.conferenceId = conferenceId;
        this.map = map;
    }

    public String getConferenceId() {
        return conferenceId;
    }

    @Override
    public void rejoin() {
        Log.d("WaitingJoinHolder", "rejoin: conferenceId:=" + conferenceId);
        module.join(conferenceId, map, promise);
        module = null;
        map = null;
        promise = null;
    }
}
