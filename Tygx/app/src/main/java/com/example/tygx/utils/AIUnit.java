package com.example.tygx.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.aiunit.common.protocol.ocr.OCRItem;
import com.aiunit.common.protocol.ocr.OCRItemCollection;
import com.aiunit.common.protocol.types.Point;
import com.aiunit.vision.common.ConnectionCallback;
import com.aiunit.vision.ocr.OCRInputSlot;
import com.aiunit.vision.ocr.OCROutputSlot;
import com.coloros.ocs.ai.cv.CVUnit;
import com.coloros.ocs.ai.cv.CVUnitClient;
import com.coloros.ocs.base.common.ConnectionResult;
import com.coloros.ocs.base.common.api.OnConnectionFailedListener;
import com.coloros.ocs.base.common.api.OnConnectionSucceedListener;

import java.util.ArrayList;
import java.util.List;

public class AIUnit {
    private CVUnitClient mCVClient;
    private int startCode = -1;

    public AIUnit(Context context) {
        // 初始化算法类
        mCVClient = CVUnit.getOCRARDetectorClient(context)
                .addOnConnectionSucceedListener(new OnConnectionSucceedListener() {
                    @Override
                    public void onConnectionSucceed() {
                        Log.i("AIUnit", " authorize connect: onConnectionSucceed");
                    }
                }).addOnConnectionFailedListener(new OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.e("AIUnit", " authorize connect: onFailure: " + connectionResult.getErrorCode());
                    }
                });

        // 连接AIUnit服务
        mCVClient.initService(context, new ConnectionCallback() {
            @Override
            public void onServiceConnect() {
                Log.i("AIUnit", "initService: onServiceConnect");
                startCode = mCVClient.start();
                Log.i("AIUnit", "mCVClient is ready");
            }

            @Override
            public void onServiceDisconnect() {
                Log.e("AIUnit", "initService: onServiceDisconnect: ");
            }
        });
    }

    public List<String> OCR(Bitmap bitmap) {
        List<String> result = new ArrayList<>();
        if (bitmap == null) {
            Log.e("AIUnit", "bitmap is null");
        } else {
            if (startCode == 0) {
                // 表示AIUnit服务连接成功，可以使用
                OCRInputSlot inputSlot = (OCRInputSlot) mCVClient.createInputSlot();
                inputSlot.setTargetBitmap(bitmap);
                OCROutputSlot outputSlot = (OCROutputSlot) mCVClient.createOutputSlot();

                mCVClient.process(inputSlot, outputSlot);
                OCRItemCollection ocrItemCollection = outputSlot.getOCRItemCollection();
                List<OCRItem> ocrItemList = ocrItemCollection.getOrcItemList();
                for (OCRItem ocrItem : ocrItemList) {
                    result.add(ocrItem.getText());
                }
            } else {
                Log.w("AIUnit", "mCVClient is not ready");
            }
        }
        return result;
    }

    public void release() {
        if (mCVClient != null) {
            mCVClient.stop();
            mCVClient.releaseService();
        }
        mCVClient = null;
    }
}
