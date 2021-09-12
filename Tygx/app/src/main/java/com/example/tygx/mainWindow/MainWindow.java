package com.example.tygx.mainWindow;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.tygx.R;
import com.example.tygx.databinding.ActivityMainWindowBinding;
import com.example.tygx.inputUrl.InputUrl;
import com.example.tygx.myAbstract.MyAbstract;
import com.example.tygx.snpe.Predictor;
import com.example.tygx.snpe.Utils;
import com.example.tygx.utils.BaseActivity;


/*
 * 登录之后的主界面
 * 可以选择开始，看历史摘要或者帮助
 */
public class MainWindow extends BaseActivity {

    private static final int REQ_ABSTRACT = 1;
    private static final int REQ_MY_ABSTRACT = 1;

    // Model settings of object detection
    protected String modelPath = "";
    protected String labelPath = "";
    protected String imagePath = "";
    protected int cpuThreadNum = 1;
    protected String cpuPowerMode = "";
    protected String inputColorFormat = "";
    protected long[] inputShape = new long[]{};
    protected float[] inputMean = new float[]{};
    protected float[] inputStd = new float[]{};
    protected float scoreThreshold = 0.1f;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainWindowBinding inflate = ActivityMainWindowBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//设置statusbar为透明色
        getWindow().setNavigationBarColor(Color.TRANSPARENT);//设置NavigationBar为透明色

        //一键开始
        inflate.buttonStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainWindow.this, InputUrl.class);
            startActivityForResult(intent, REQ_ABSTRACT);
        });

        //我的摘要
        inflate.buttonMyAbstract.setOnClickListener(v -> {
            Intent intent = new Intent(MainWindow.this, MyAbstract.class);
            startActivityForResult(intent, REQ_MY_ABSTRACT);
        });

        //帮助说明
        inflate.buttonHelp.setOnClickListener(v -> new AlertDialog.Builder(MainWindow.this)
                .setTitle("帮助说明")
                .setMessage("1.点击“一键开始”，输入视频URL提交后，等待数分钟即可获得图文摘要；\n" +
                        "2.点击“我的摘要”可查看摘要历史。")
                .setPositiveButton("好", null)
                .show());

        // init model
        initSNPE();
    }

    @Override
    protected void onDestroy() {
        if (Global.SNPE != null) {
            Global.SNPE.releaseModel();
        }
        super.onDestroy();
    }

    void initSNPE() {
        modelPath = getString(R.string.MODEL_PATH_DEFAULT);
        labelPath = getString(R.string.LABEL_PATH_DEFAULT);
        cpuThreadNum = Integer.parseInt(getString(R.string.CPU_THREAD_NUM_DEFAULT));
        cpuPowerMode = getString(R.string.CPU_POWER_MODE_DEFAULT);
        inputColorFormat = getString(R.string.INPUT_COLOR_FORMAT_DEFAULT);
        inputShape = Utils.parseLongsFromString(getString(R.string.INPUT_SHAPE_DEFAULT), ",");
        inputMean = Utils.parseFloatsFromString(getString(R.string.INPUT_MEAN_DEFAULT), ",");
        inputStd = Utils.parseFloatsFromString(getString(R.string.INPUT_STD_DEFAULT), ",");
        scoreThreshold = Float.parseFloat(getString(R.string.SCORE_THRESHOLD_DEFAULT));

        Global.SNPE = new Predictor();
        boolean loadStatue = Global.SNPE.init(MainWindow.this, modelPath, labelPath, cpuThreadNum, cpuPowerMode, inputColorFormat, inputShape, inputMean, inputStd, scoreThreshold);
        Log.d("MainWindow", "loadStatue: " + loadStatue);
    }
}


