package com.example.tygx.showAbstract.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.tygx.data.repository.Abstract;
import com.example.tygx.data.repository.AbstractsManager;
import com.example.tygx.databinding.ActivityAbstractBinding;
import com.example.tygx.databinding.FragmentPicTextBinding;
import com.example.tygx.showAbstract.adapter.ImageAdapter;
import com.example.tygx.showAbstract.bean.DataBean;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.tygx.utils.Tools.hexString2Bytes;

public class PicTextFragment extends Fragment implements ObservableScrollViewCallbacks {
    private FragmentPicTextBinding binding;
    ObservableScrollView scrollView;
    Activity context;
    String jobId;
    JSONArray textArray;
    String text = "";
    Banner<DataBean, ImageAdapter> banner;
    List<DataBean> bannerImageList = Collections.synchronizedList(new ArrayList<>());

    @Override
    public View  onCreateView (@NotNull LayoutInflater inflater,
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
        String result = mAbstract.getResult();
        StringBuilder sb = new StringBuilder();
        try {
            //text
            JSONObject resultJson = new JSONObject(result);
            textArray = resultJson.getJSONArray("text");
            int text_index = 0;
            while (text_index < textArray.length()) {
                sb.append(textArray.get(text_index).toString());
                sb.append("\n\n\n\n");
                text_index++;
            }
            text = sb.toString();
        }catch (JSONException e) {
            Log.e("textAbstract","json load failed");
        }
        try{
            //picture
            JSONObject resultJson = new JSONObject(result);
            JSONObject pictureJson = new JSONObject(resultJson.get("pictures").toString());
            int frame_i = 1;
            String frame_key = "keyframe_" + frame_i + ".jpg";
            while(pictureJson.has(frame_key)){
                byte[] bitmapArray = Base64.decode(pictureJson.get(frame_key).toString(), Base64.DEFAULT);
//                byte[] bitmapArray = pictureJson.get(frame_key).toString().getBytes();
                bannerImageList.add(new DataBean(bitmapArray, null, 8));
                frame_i++;
                frame_key = "keyframe_" + frame_i + ".jpg";
            }
        }catch (JSONException e) {
            Log.e("textAbstract","json load failed");
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
        args.putString("jobId",jobId);
        f.setArguments(args);
        return f;
    }


}
