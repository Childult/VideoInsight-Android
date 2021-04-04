package com.example.tygx.showAbstract.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.tygx.databinding.FragmentSubsectionBinding;
import com.example.tygx.databinding.FragmentSubtitleBinding;
import com.example.tygx.databinding.FragmentVideoCaptionBinding;

import org.jetbrains.annotations.NotNull;

public class VideoCaptionFragment extends Fragment {
    private FragmentVideoCaptionBinding binding;

    @Override
    public View  onCreateView (@NotNull LayoutInflater inflater,
                               ViewGroup container,
                               Bundle savedInstanceState) {
        FragmentActivity context = getActivity();
        super.onCreate(savedInstanceState);
        binding = FragmentVideoCaptionBinding.inflate(inflater, container, false);
        binding.textView2.setText("视频字幕");
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static VideoCaptionFragment getInstance(int index, String jobId) {
        VideoCaptionFragment f = new VideoCaptionFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putString("jobId",jobId);
        f.setArguments(args);
        return f;
    }
}
