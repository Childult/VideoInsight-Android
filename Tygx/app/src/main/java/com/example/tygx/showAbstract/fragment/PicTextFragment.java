package com.example.tygx.showAbstract.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.tygx.data.repository.Abstract;
import com.example.tygx.data.repository.AbstractsManager;
import com.example.tygx.databinding.FragmentPicTextBinding;
import com.example.tygx.showAbstract.adapter.ImageAdapter;
import com.example.tygx.showAbstract.bean.DataBean;
import com.example.tygx.utils.AIUnit;
import com.example.tygx.utils.Global;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PicTextFragment extends Fragment implements ObservableScrollViewCallbacks {
    private FragmentPicTextBinding binding;
    ObservableScrollView scrollView;
    Activity context;
    String jobId;
    JSONArray textArray;
    String text = "";
    Banner<DataBean, ImageAdapter> banner;
    List<DataBean> bannerImageList = Collections.synchronizedList(new ArrayList<>());
    List<DataBean> bannerImageListWithoutKW = Collections.synchronizedList(new ArrayList<>());
//    AIUnit aiUnit;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentActivity context = getActivity();
        super.onCreate(savedInstanceState);
        binding = FragmentPicTextBinding.inflate(inflater, container, false);
        context = this.getActivity();
        scrollView = binding.list;
        scrollView.setScrollViewCallbacks(this);
        jobId = getArguments().getString("jobId");
        Abstract mAbstract = AbstractsManager.getIntance(context).abstractsDao().loadByJobId(jobId);
        String[] keywords = mAbstract.getKeywords().split(" ");
//        if (keywords.length > 0) {
//            Log.i("PicText", "AI Unit 初始化");
//            aiUnit = new AIUnit(Global.CONTEXT);
//        }

        String result = mAbstract.getResult();
        StringBuilder sb = new StringBuilder();
        try {
            //text
            JSONObject resultJson = new JSONObject(result);
            textArray = resultJson.getJSONArray("text");
            int text_index = 0;
            while (text_index < textArray.length()) {
                sb.append(textArray.get(text_index).toString());
                sb.append("\n\n");
                text_index++;
            }
            text = sb.toString();
        } catch (JSONException e) {
            Log.e("textAbstract", "json load failed");
        }
        try {
            //picture
            JSONObject resultJson = new JSONObject(result);
            JSONObject pictureJson = new JSONObject(resultJson.get("pictures").toString());
            int frame_i = 1;
            String frame_key = "keyframe_" + frame_i + ".jpg";
            while (pictureJson.has(frame_key)) {
                byte[] bitmapArray = Base64.decode(pictureJson.get(frame_key).toString(), Base64.DEFAULT);

                if (keywords.length > 0) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                    List<String> res = Global.AIUNIT.OCR(bitmap);
                    boolean hasKeywords = false;
                    for (String sen: res) {
                        if (hasKeywords) {
                            break;
                        }

                        for (String kw: keywords) {
                            // OCR结果中有关键词
                            if (sen.contains(kw)) {
                                hasKeywords = true;
                                break;
                            }
                        }
                    }

                    if (hasKeywords) {
                        bannerImageList.add(new DataBean(bitmapArray, null, 8));
                    } else {
                        bannerImageListWithoutKW.add(new DataBean(bitmapArray, null, 8));
                    }
                } else {
                    bannerImageList.add(new DataBean(bitmapArray, null, 8));
                }

                frame_i++;
                frame_key = "keyframe_" + frame_i + ".jpg";
            }

            if (bannerImageListWithoutKW.size() > 0) {
                // 将不包含关键词的图片放在包含关键字的图片后面
                bannerImageList.addAll(bannerImageListWithoutKW);
            }

//            if (aiUnit != null) {
//                aiUnit.release();
//            }
        } catch (JSONException e) {
            Log.e("videoAbstract", "json load failed");
        }
        binding.picTextTextView.setText(text);

        banner = binding.banner;
        useBanner();

        return binding.getRoot();
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    public void useBanner() {
        //--------------------------简单使用-------------------------------
        banner.setAdapter(new ImageAdapter(bannerImageList))
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this.getContext()));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static PicTextFragment getInstance(int index, String jobId) {
        PicTextFragment f = new PicTextFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putString("jobId", jobId);
        f.setArguments(args);
        return f;
    }


}
