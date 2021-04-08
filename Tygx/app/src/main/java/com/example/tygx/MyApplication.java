package com.example.tygx;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tygx.utils.AIUnit;
import com.example.tygx.utils.Global;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.installations.FirebaseInstallations;
import com.kingja.loadsir.callback.ProgressCallback;
import com.kingja.loadsir.core.LoadSir;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        //获取FID
        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            Global.fID = task.getResult();
                            Log.d("Installations", "Installation ID: " + task.getResult());
                        } else {
                            Log.e("Installations", "Unable to get Installation ID");
                        }
                    }
                });
        //将获取到的FID码打印
        Log.d("FID","FID: " + Global.fID);

        ProgressCallback loadingCallback = new ProgressCallback.Builder()
                .setTitle("Loading")
                .build();
        LoadSir.beginBuilder()
                .addCallback(loadingCallback)
                .setDefaultCallback(ProgressCallback.class) //设置默认状态页
                .commit();

        Global.CONTEXT = getApplicationContext();
        Global.AIUNIT = new AIUnit(Global.CONTEXT);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Global.AIUNIT.release();
    }
}
