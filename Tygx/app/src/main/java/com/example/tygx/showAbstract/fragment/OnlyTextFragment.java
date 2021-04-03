package com.example.tygx.showAbstract.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tygx.R;
import com.example.tygx.data.repository.Abstract;
import com.example.tygx.data.repository.AbstractsManager;
import com.example.tygx.databinding.FragmentOnlyTextBinding;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OnlyTextFragment extends Fragment implements ObservableScrollViewCallbacks {
    private FragmentOnlyTextBinding binding;
    ObservableScrollView scrollView;
    Activity context;
    String jobId;
    JSONArray textArray;
    String text = "";

    @Override
    public View  onCreateView (@NotNull LayoutInflater inflater,
                               ViewGroup container,
                               Bundle savedInstanceState) {
        FragmentActivity context = getActivity();
        super.onCreate(savedInstanceState);
        binding = FragmentOnlyTextBinding.inflate(inflater, container, false);
        context = this.getActivity();
        scrollView = binding.list;
        scrollView.setScrollViewCallbacks(this);
        jobId = getArguments().getString("jobId");
        Abstract mAbstract = AbstractsManager.getIntance(context).abstractsDao().loadByJobId(jobId);
        String result = mAbstract.getResult();
        StringBuilder sb = new StringBuilder();
        try{
            JSONObject resultJson = new JSONObject(result);
            textArray = resultJson.getJSONArray("text");
            int text_index = 0;
            while (text_index < textArray.length()){
               sb.append(textArray.get(text_index).toString());
               sb.append("\n");
                text_index++;
            }
        }catch (JSONException e) {
            Log.e("textAbstract","json load failed");
        }
        text = sb.toString();
        binding.textView.setText(text);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static OnlyTextFragment getInstance(int index, String jobId) {
        OnlyTextFragment f = new OnlyTextFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putString("jobId",jobId);
        f.setArguments(args);
        return f;
    }
}
