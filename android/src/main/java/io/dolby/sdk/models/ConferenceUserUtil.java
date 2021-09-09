package io.dolby.sdk.models;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.voxeet.sdk.json.ParticipantInfo;
import com.voxeet.sdk.models.Participant;
import com.voxeet.sdk.models.v1.ConferenceParticipantStatus;
import com.voxeet.sdk.models.v1.SdkParticipant;
import com.voxeet.sdk.utils.Opt;

public final class ConferenceUserUtil {
    public final static String PARTICIPANT_ID = "userId";
    public final static String PARTICIPANT_NAME = "name";
    public final static String PARTICIPANT_EXTERNAL_ID = "externalId";
    public final static String PARTICIPANT_AVATAR_URL = "avatarUrl";
    public final static String CONFERENCE_STATUS = "conferenceStatus";

    private ConferenceUserUtil() { }

    @NonNull
    public static WritableMap toMap(@NonNull Participant user) {
        ParticipantInfo userInfo = user.getInfo();

        WritableMap map = new WritableNativeMap();
        map.putString(PARTICIPANT_ID, user.getId());
        map.putString(CONFERENCE_STATUS, Opt.of(user.getStatus()).or(ConferenceParticipantStatus.UNKNWON).name());

        if (null != userInfo) {
            if (null != userInfo.getName()) {
                map.putString(PARTICIPANT_NAME, userInfo.getName());
            }
            if (null != userInfo.getExternalId()) {
                map.putString(PARTICIPANT_EXTERNAL_ID, userInfo.getExternalId());
            }
            if (null != userInfo.getAvatarUrl()) {
                map.putString(PARTICIPANT_AVATAR_URL, userInfo.getAvatarUrl());
            }
        }

        return map;
    }

    @NonNull
    public static WritableMap toMap(@NonNull ParticipantInfo participantInfo) {
        WritableMap map = new WritableNativeMap();

        if (participantInfo != null) {
            if (participantInfo.getName() != null) {
                map.putString(PARTICIPANT_NAME, participantInfo.getName());
            }
            if (participantInfo.getExternalId() != null) {
                map.putString(PARTICIPANT_EXTERNAL_ID, participantInfo.getExternalId());
            }
            if (participantInfo.getAvatarUrl() != null) {
                map.putString(PARTICIPANT_AVATAR_URL, participantInfo.getAvatarUrl());
            }
        }

        return map;
    }

    public static WritableArray toMap(Iterable<Participant> conferenceUsers) {
        WritableNativeArray array = new WritableNativeArray();
        if (null != conferenceUsers) {
            for (Participant user : conferenceUsers) {
                array.pushMap(toMap(user));
            }
        }
        return array;
    }

    public static WritableMap toMap(SdkParticipant user) {
        WritableMap map = new WritableNativeMap();
        map.putString(PARTICIPANT_ID, user.getUserId());
        map.putString(CONFERENCE_STATUS, Opt.of(user.getStatus()).or(ConferenceParticipantStatus.UNKNWON.toString()));

        if (null != user.getMetadata()) {
            if (null != user.getMetadata().getExternalName()) {
                map.putString(PARTICIPANT_NAME, user.getMetadata().getExternalName());
            }
            if (null != user.getMetadata().getExternalId()) {
                map.putString(PARTICIPANT_EXTERNAL_ID, user.getMetadata().getExternalId());
            }
            if (null != user.getMetadata().getExternalPhotoUrl()) {
                map.putString(PARTICIPANT_AVATAR_URL, user.getMetadata().getExternalPhotoUrl());
            }
        }

        return map;
    }

    public static ParticipantInfo toParticipantInfo(ReadableMap map) {
        return new ParticipantInfo(
                map.getString(PARTICIPANT_NAME),
                map.getString(PARTICIPANT_EXTERNAL_ID),
                map.getString(PARTICIPANT_AVATAR_URL)
        );
    }

}
