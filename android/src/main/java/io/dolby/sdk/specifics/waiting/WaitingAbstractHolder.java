package io.dolby.sdk.specifics.waiting;

import com.facebook.react.bridge.Promise;

import io.dolby.sdk.services.RNConferenceServiceModule;

public abstract class WaitingAbstractHolder {
    protected RNConferenceServiceModule module;
    protected Promise promise;

    protected WaitingAbstractHolder(RNConferenceServiceModule module, Promise promise) {
        this.module = module;
        this.promise = promise;
    }

    public Promise getPromise() {
        return promise;
    }

    public abstract void rejoin();
}
