package com.example.tygx.showAbstract.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.tygx.databinding.ActivityAbstractBinding;
import com.example.tygx.databinding.FragmentPicTextBinding;

import org.jetbrains.annotations.NotNull;

public class PicTextFragment extends Fragment {
    private FragmentPicTextBinding binding;

    @Override
    public View  onCreateView (@NotNull LayoutInflater inflater,
                               ViewGroup container,
                               Bundle savedInstanceState) {
        FragmentActivity context = getActivity();
        super.onCreate(savedInstanceState);
        binding = FragmentPicTextBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static PicTextFragment getInstance(int index) {
        PicTextFragment f = new PicTextFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }
}
