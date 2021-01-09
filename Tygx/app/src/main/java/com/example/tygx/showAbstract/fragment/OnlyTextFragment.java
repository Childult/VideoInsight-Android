package com.example.tygx.showAbstract.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tygx.R;
import com.example.tygx.databinding.FragmentOnlyTextBinding;
import com.example.tygx.databinding.FragmentPicTextBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class OnlyTextFragment extends Fragment {
    private FragmentOnlyTextBinding binding;

    @Override
    public View  onCreateView (@NotNull LayoutInflater inflater,
                               ViewGroup container,
                               Bundle savedInstanceState) {
        FragmentActivity context = getActivity();
        super.onCreate(savedInstanceState);
        binding = FragmentOnlyTextBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static OnlyTextFragment getInstance(int index) {
        OnlyTextFragment f = new OnlyTextFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }
}
