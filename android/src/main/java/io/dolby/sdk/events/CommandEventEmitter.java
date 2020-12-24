package io.dolby.sdk.events;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.voxeet.sdk.events.sdk.MessageReceived;
import com.voxeet.sdk.json.BroadcastEvent;
import com.voxeet.sdk.services.chat.ChatMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.dolby.sdk.models.ConferenceUserUtil;

public class CommandEventEmitter extends AbstractEventEmitter {

    public CommandEventEmitter(@NonNull ReactContext context, @NonNull EventBus eventBus) {
        super(context, eventBus);

        register(new EventFormatterCallback<ChatMessageEvent>(ChatMessageEvent.class) {
            @Override
            void transform(@NonNull WritableMap map, @NonNull ChatMessageEvent instance) {
                WritableMap mapMessage = new WritableNativeMap();
                mapMessage.putString("content", instance.message.content);
                mapMessage.putString("date", instance.message.date.toString());
                mapMessage.putString("type", instance.message.type.text());
                map.putMap("message", mapMessage);

                map.putMap("participant", ConferenceUserUtil.toMap(instance.participant));
            }
        }).register(new EventFormatterCallback<BroadcastEvent>(BroadcastEvent.class) {
            @Override
            void transform(@NonNull WritableMap map, @NonNull BroadcastEvent instance) {
                map.putString("message", instance.message);
                map.putString("participantId", instance.participantId);
            }
        }).register(new EventFormatterCallback<MessageReceived>(MessageReceived.class) {
            @Override
            void transform(@NonNull WritableMap map, @NonNull MessageReceived instance) {
                map.putString("conferenceId", instance.conferenceId);
                map.putString("message", instance.message);
                map.putString("participantId", instance.participantId);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChatMessageEvent event) {
        emit(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BroadcastEvent event) {
        emit(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageReceived event) {
        emit(event);
    }
}
