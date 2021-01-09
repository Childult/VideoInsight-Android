package com.example.tygx.showAbstract.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.tygx.databinding.FragmentPicTextBinding;
import com.example.tygx.databinding.FragmentSubsectionBinding;

import org.jetbrains.annotations.NotNull;

public class SubsectionFragment extends Fragment {
    private FragmentSubsectionBinding binding;

    @Override
    public View  onCreateView (@NotNull LayoutInflater inflater,
                               ViewGroup container,
                               Bundle savedInstanceState) {
        FragmentActivity context = getActivity();
        super.onCreate(savedInstanceState);
        binding = FragmentSubsectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static SubsectionFragment getInstance(int index) {
        SubsectionFragment f = new SubsectionFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }
}
