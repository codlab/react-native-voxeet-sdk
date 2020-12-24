package io.dolby.sdk.events;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.voxeet.sdk.json.RecordingStatusUpdatedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RecordingEventEmitter extends AbstractEventEmitter {

    public RecordingEventEmitter(@NonNull ReactContext context, @NonNull EventBus eventBus) {
        super(context, eventBus);

        register(new EventFormatterCallback<RecordingStatusUpdatedEvent>(RecordingStatusUpdatedEvent.class) {
            @Override
            void transform(@NonNull WritableMap map, @NonNull RecordingStatusUpdatedEvent instance) {
                map.putString("conferenceId", instance.conferenceId);
                map.putString("userId", instance.participantId);
                map.putString("recordingStatus", instance.recordingStatus);
                map.putString("type", instance.getType());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RecordingStatusUpdatedEvent event) {
        emit(event);
    }
}
