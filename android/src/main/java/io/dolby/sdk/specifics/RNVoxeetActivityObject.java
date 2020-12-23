package io.dolby.sdk.specifics;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.voxeet.VoxeetSDK;
import com.voxeet.sdk.events.error.PermissionRefusedEvent;
import com.voxeet.sdk.services.ConferenceService;
import com.voxeet.sdk.services.ScreenShareService;
import com.voxeet.sdk.services.screenshare.RequestScreenSharePermissionEvent;
import com.voxeet.sdk.utils.Validate;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RNVoxeetActivityObject {

    private Activity mActivity;

    public void onCreate(@NonNull Activity activity) {
        mActivity = activity;
    }

    public void onResume(@NonNull Activity activity) {
        if (null != VoxeetSDK.instance()) {
            VoxeetSDK.instance().register(this);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this); //registering this activity
        }

        /*if (canBeRegisteredToReceiveCalls()) {
            IncomingCallFactory.setTempAcceptedIncomingActivity(BounceVoxeetActivity.class);
            IncomingCallFactory.setTempExtras(activity.getIntent().getExtras());
        }

        if (mIncomingBundleChecker.isBundleValid()) {
            if (null != VoxeetSDK.instance()) {
                mIncomingBundleChecker.onAccept();
            } else {
                //RNVoxeetConferencekitModule.AWAITING_OBJECT = this;
            }
        }*/

        ScreenShareService screenShareService = VoxeetSDK.screenShare();
        if (null != screenShareService) {
            screenShareService.consumeRightsToScreenShare();
        }
    }

    /*@Nullable
    public IncomingBundleChecker getIncomingBundleChecker() {
        return mIncomingBundleChecker;
    }*/

    public void onPause(@NonNull Activity activity) {
        ConferenceService conferenceService = VoxeetSDK.conference();
        if (null != conferenceService) {
            //stop fetching stats if any pending
            if (!conferenceService.isLive()) {
                VoxeetSDK.localStats().stopAutoFetch();
            }
        }
        if (mActivity == activity) mActivity = null;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionRefusedEvent.RESULT_CAMERA: {
                ConferenceService conferenceService = VoxeetSDK.conference();
                if (null != conferenceService && conferenceService.isLive()) {
                    VoxeetSDK
                            .conference()
                            .startVideo()
                            .then(result -> {

                            })
                            .error(Throwable::printStackTrace);
                }
                return;
            }
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PermissionRefusedEvent event) {
        if (null != event.getPermission()) {
            switch (event.getPermission()) {
                case CAMERA:
                    Validate.requestMandatoryPermissions(mActivity, new String[]{ Manifest.permission.CAMERA }, PermissionRefusedEvent.RESULT_CAMERA);
                    break;
            }
        }
    }

    public void onNewIntent(Intent intent) {
        /*mIncomingBundleChecker = new IncomingBundleChecker(intent, null);
        if (mIncomingBundleChecker.isBundleValid()) {
            if (null != VoxeetSDK.instance()) {
                mIncomingBundleChecker.onAccept();
            } else {
                //RNVoxeetConferencekitModule.AWAITING_OBJECT = this;
            }
        }*/
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean managed = false;
        ScreenShareService screenShareService = VoxeetSDK.screenShare();
        if (null != screenShareService) {
            managed = screenShareService.onActivityResult(requestCode, resultCode, data);
        }

        return managed;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RequestScreenSharePermissionEvent event) {
        VoxeetSDK.screenShare().sendUserPermissionRequest(mActivity);
    }

}
