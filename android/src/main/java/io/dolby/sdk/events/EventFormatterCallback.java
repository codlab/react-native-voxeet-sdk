package io.dolby.sdk.events;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.WritableMap;

public abstract class EventFormatterCallback<TYPE> {

    private final String _name;
    private final Class<TYPE> _klass;

    protected EventFormatterCallback(Class<TYPE> klass) {
        _name = klass.getSimpleName();
        _klass = klass;
    }

    @NonNull
    String name() {
        return _name;
    }

    @NonNull
    Class<TYPE> getKlass() {
        return _klass;
    }

    abstract void transform(@NonNull WritableMap map, @NonNull TYPE instance);
}
