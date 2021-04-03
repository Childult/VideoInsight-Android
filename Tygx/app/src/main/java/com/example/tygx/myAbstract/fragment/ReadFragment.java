package com.example.tygx.myAbstract.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tygx.data.TaskListSharedPreference;
import com.example.tygx.data.repository.Abstract;
import com.example.tygx.data.repository.AbstractsManager;
import com.example.tygx.inputUrl.InputUrl;
import com.example.tygx.myAbstract.adapter.MyAbstractRecyclerViewAdapter;
import com.example.tygx.myAbstract.fragment.dummy.DummyContent;
import com.example.tygx.showAbstract.ShowAbstract;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//已完成列表
public class ReadFragment extends Fragment {
    private com.example.tygx.databinding.FragmentDoneAbstractListBinding binding;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private final Lock lock = new ReentrantLock();
    //using DummyItem
    //protected MyAbstractRecyclerViewAdapter<DummyContent.DummyItem> myAbstractRecyclerViewAdapter;
    //protected List<DummyContent.DummyItem> items = Collections.synchronizedList(new ArrayList<>());
    //using Abstract
    protected MyAbstractRecyclerViewAdapter<Abstract> myAbstractRecyclerViewAdapter;
    protected List<Abstract> items = Collections.synchronizedList(new ArrayList<>());
    protected Set<String> readTaskSet;
    protected int readNums;
    protected Context context;
    protected Activity mActivity;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReadFragment() {
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
        RecyclerView recyclerView = binding.recyclerView;
        context = recyclerView.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        //using SharedPreference
        /*SharedPreferences sPreferences = context.getSharedPreferences("taskList", Context.MODE_PRIVATE);
        if(sPreferences.contains("readTaskSet")) {
            readTaskSet = sPreferences.getStringSet("readTaskSet", new HashSet<String>());
            readNums = readTaskSet.size();
        }else {
            readTaskSet = new HashSet<>();
            readNums = 0;
        }
        int i = 0;
        for(String readTask : readTaskSet){
            items.add(new DummyContent.DummyItem(String.valueOf(i),"read abstract","读过的摘要","已完成",readTask,""));
            i++;
        }*/
        items = AbstractsManager.getIntance(context).abstractsDao().loadByType("已读");
        myAbstractRecyclerViewAdapter = new MyAbstractRecyclerViewAdapter<>(items, context);
        recyclerView.setAdapter(myAbstractRecyclerViewAdapter);
        // 设置点击事件
        myAbstractRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                    showDetailedAbstract(position);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(new ColorDrawable(ContextCompat.getColor(context, android.R.color.darker_gray)));
        recyclerView.addItemDecoration(dividerItemDecoration);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        int i = 0;
        SharedPreferences sPreferences = context.getSharedPreferences("taskList", Context.MODE_PRIVATE);
        Set<String> readTaskSet;
        if(sPreferences.contains("readTaskSet")) {
            readTaskSet = sPreferences.getStringSet("readTaskSet", new HashSet<String>());
        }else {
            readTaskSet = new HashSet<>();
        }
        for(String jobId: readTaskSet){
            myAbstractRecyclerViewAdapter.notifyItemChanged(i);
            i++;
        }
    }

    private void showDetailedAbstract(int position) {
        Intent intent = new Intent(getContext(), ShowAbstract.class);
        //DummyContent.DummyItem item = items.get(position);
        Abstract item = items.get(position);
        intent.putExtra("jobId", item.getJobId());
        startActivity(intent);
    }

    public static ReadFragment getInstance(int index) {
        ReadFragment f = new ReadFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, index);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //保存activity引用

        if (context instanceof Activity){
            this.mActivity=(Activity) context;
        }
    }
}
