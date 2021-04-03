package com.example.tygx.showAbstract.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.tygx.databinding.FragmentPicTextBinding;
import com.example.tygx.databinding.FragmentSubtitleBinding;

import org.jetbrains.annotations.NotNull;

public class SubtitleFragment extends Fragment {
    private FragmentSubtitleBinding binding;

    @Override
    public View  onCreateView (@NotNull LayoutInflater inflater,
                               ViewGroup container,
                               Bundle savedInstanceState) {
        FragmentActivity context = getActivity();
        super.onCreate(savedInstanceState);
        binding = FragmentSubtitleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static SubtitleFragment getInstance(int index, String jobId) {
        SubtitleFragment f = new SubtitleFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putString("jobId",jobId);
        f.setArguments(args);
        return f;
    }
}
