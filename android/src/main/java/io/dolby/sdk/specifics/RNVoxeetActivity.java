package io.dolby.sdk.specifics;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.facebook.react.ReactActivity;

import io.dolby.sdk.services.RNConferenceServiceModule;
import io.dolby.sdk.specifics.waiting.WaitingAbstractHolder;

public abstract class RNVoxeetActivity extends ReactActivity {

    private RNVoxeetActivityObject mActivityObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityObject = new RNVoxeetActivityObject();
        mActivityObject.onCreate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RNConferenceServiceModule.registerActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        WaitingAbstractHolder holder = RNConferenceServiceModule.getWaitingJoinHolder();

        if (null != holder && RNConferenceServiceModule.isWaiting()) {
            int i = 0;

            while (i < permissions.length && i < grantResults.length) {
                if (Manifest.permission.RECORD_AUDIO.equals(permissions[i])) {
                    if (PackageManager.PERMISSION_GRANTED == grantResults[i]) {
                        holder.rejoin();
                    } else {
                        try {
                            throw new IllegalStateException("No mic permission granted, can't join");
                        } catch (Exception e) {
                            e.printStackTrace();
                            holder.getPromise().reject("NO_MIC_PERMISSION", e);
                        }
                    }
                    //managed
                    return;
                }
                i++;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        mActivityObject.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
