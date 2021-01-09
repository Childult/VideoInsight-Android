package com.example.tygx.myAbstract.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tygx.databinding.FragmentOnlyTextBinding;
import com.example.tygx.myAbstract.adapter.MyAbstractRecyclerViewAdapter;
import com.example.tygx.myAbstract.fragment.dummy.DummyContent;

import org.jetbrains.annotations.NotNull;

//已完成列表
public class DoneFragment extends Fragment {
    private com.example.tygx.databinding.FragmentDoneAbstractListBinding binding;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DoneFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View  onCreateView (@NotNull LayoutInflater inflater,
                               ViewGroup container,
                               Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.tygx.databinding.FragmentDoneAbstractListBinding.inflate(inflater, container, false);
        RecyclerView view = binding.getRoot();
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            view.setLayoutManager(new LinearLayoutManager(context));
        } else {
            view.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        view.setAdapter(new MyAbstractRecyclerViewAdapter(DummyContent.ITEMS));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static DealingFragment getInstance(int index) {
        DealingFragment f = new DealingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, index);
        f.setArguments(args);
        return f;
    }
}
