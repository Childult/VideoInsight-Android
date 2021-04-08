package com.example.tygx.myAbstract.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tygx.data.TaskListSharedPreference;
import com.example.tygx.data.repository.Abstract;
import com.example.tygx.data.repository.AbstractsManager;
import com.example.tygx.databinding.FragmentDoneAbstractListBinding;
import com.example.tygx.inputUrl.InputUrl;
import com.example.tygx.myAbstract.adapter.MyAbstractRecyclerViewAdapter;
import com.example.tygx.myAbstract.fragment.dummy.DummyContent;
import com.example.tygx.request.GetTask;
import com.example.tygx.request.PostCreateTask;
import com.example.tygx.showAbstract.ShowAbstract;
import com.example.tygx.utils.Global;
import com.example.tygx.utils.Status;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Flowable;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

//处理中列表
public class UnreadFragment extends Fragment {
    private FragmentDoneAbstractListBinding binding;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    protected RecyclerView recyclerView;
    protected SmartRefreshLayout refreshLayout;
    //using DummyItem
    //protected MyAbstractRecyclerViewAdapter<DummyContent.DummyItem> myAbstractRecyclerViewAdapter;
    //protected List<DummyContent.DummyItem> items = Collections.synchronizedList(new ArrayList<>());
    //protected Set<String> unreadTaskSet;
    //using Abstract
    protected MyAbstractRecyclerViewAdapter<Abstract> myAbstractRecyclerViewAdapter;
    protected List<Abstract> items = Collections.synchronizedList(new ArrayList<>());
//    protected Flowable<List<Abstract>> itemsFlow;

    protected Activity mActivity;

    protected int unreadNums;
    protected Context context;
    LoadService loadService;
    Handler handler = new Handler();

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            checkRefresh();
        }
    };

    /******************************
     ************ 回调 ************
     ******************************/
    private final okhttp3.Callback getTask = new okhttp3.Callback() {
        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            try {
                if (response.code() != 200) {
                    mActivity.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(mActivity, "获取任务状态失败", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    });
                } else {
                    ResponseBody responseBody = response.body();
                    String responseBodyString = responseBody != null ? responseBody.string() : "";
                    if (Global.HTTP_DEBUG_MODE)
                        Log.e("HttpResponse", responseBodyString);
                    JSONObject jsonObject = new JSONObject(responseBodyString);

                    String title = jsonObject.getString("title").trim();
                    Status status = Status.values()[jsonObject.getInt("status")];
                    String message = status.toString();
//                    int status = (int) jsonObject.get("status");
//                    String message = jsonObject.get("message").toString();

                    String result = "";
                    try {
                        result = jsonObject.get("result").toString();
                    } catch (JSONException e) {
                    }
                    String[] url = call.request().url().toString().split("/");
                    String finalResult = result;
                    mActivity.runOnUiThread(() -> {
                        //Toast toast = Toast.makeText(mActivity, message, Toast.LENGTH_LONG);
                        //toast.setGravity(Gravity.TOP, 0, 0);
                        //toast.show();
                        // 更新任务信息
                        refresh(url[url.length - 1], message, finalResult, String.valueOf(status), title);
                    });
                }
            } catch (JSONException e) {
                mActivity.runOnUiThread(() -> {
                    Toast toast = Toast.makeText(mActivity, "获取任务状态失败", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                });
                if (Global.HTTP_DEBUG_MODE)
                    Log.e("HttpResponse", e.toString());
            }
            mActivity.runOnUiThread(() -> {
                if (loadService != null)
                    loadService.showSuccess();
            });
        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            mActivity.runOnUiThread(() -> {
                if (loadService != null)
                    loadService.showSuccess();
            });
            mActivity.runOnUiThread(() -> {
                Toast toast = Toast.makeText(mActivity, "获取任务状态失败", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            });
            if (Global.HTTP_DEBUG_MODE)
                Log.e("HttpError", e.toString());
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UnreadFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.tygx.databinding.FragmentDoneAbstractListBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerView;
        context = recyclerView.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        /*SharedPreferences sPreferences = context.getSharedPreferences("taskList", Context.MODE_PRIVATE);
        if(sPreferences.contains("unreadTaskSet")) {
            unreadTaskSet = sPreferences.getStringSet("unreadTaskSet", new HashSet<String>());
            unreadNums = unreadTaskSet.size();
        }else {
            unreadTaskSet = new HashSet<>();
            unreadNums = 0;
        }
        int i = 0;
        for(String unreadTask : unreadTaskSet){
            items.add(new DummyContent.DummyItem(String.valueOf(i),"unread abstract","读过的摘要","未完成",unreadTask,""));
            i++;
        }*/
        items = AbstractsManager.getIntance(context).abstractsDao().loadByType("未读");
        myAbstractRecyclerViewAdapter = new MyAbstractRecyclerViewAdapter<>(items, context);
        recyclerView.setAdapter(myAbstractRecyclerViewAdapter);
        // 设置点击事件
        myAbstractRecyclerViewAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (items.get(position).message.equals(Status.DONE.toString()))
                // 若任务已完成，显示详情
                showDetailedAbstract(position);
            else if (items.get(position).message.equals(Status.CREATED.toString())) {
                // 任务处理中，显示提示信息
                mActivity.runOnUiThread(() -> {
                    Toast toast = Toast.makeText(mActivity, "任务未完成，请稍等", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                });
            } else if (items.get(position).message.equals(Status.RETRIEVE_ERR.toString())) {
                // 任务异常，显示提示信息
                mActivity.runOnUiThread(() -> {
                    Toast toast = Toast.makeText(mActivity, "任务异常，请重新创建", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                });
            }
        });

        // 设置长按删除事件
        myAbstractRecyclerViewAdapter.setOnItemLongClickListener((adapter, view, position) -> {

            mActivity.runOnUiThread(() -> new AlertDialog.Builder(mActivity)
                    .setTitle("提示")
                    .setMessage("是否确认删除该条摘要任务？")
                    .setPositiveButton("是", (dialog, which) -> {
                        Abstract mAbstract = AbstractsManager.getIntance(context).abstractsDao().loadByJobId(items.get(position).getJobId());
                        AbstractsManager.getIntance(context).abstractsDao().delete(mAbstract);
                        items.remove(position);
                        myAbstractRecyclerViewAdapter.notifyItemRemoved(position);
                    })
                    .setNegativeButton("否", null)
                    .show());
            return false;
        });

        refreshLayout = binding.refreshLayout;
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                for (Abstract item : items) {
                    // 只查询处理中的任务进度
                    String msg = item.getMessage();
//            if (msg.equals(Status.CREATED.toString()) || msg.equals("")) {
                    new Thread(() -> {
                        try {
                            new GetTask(getTask, item.getJobId()).send();
                        } catch (Exception e) {
                            Log.e("exception", e.toString());
                        }
                    }).start();
//            }
                }
                refreshLayout.finishRefresh(1000);
            }
        });


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(new ColorDrawable(ContextCompat.getColor(context, android.R.color.darker_gray)));
        recyclerView.addItemDecoration(dividerItemDecoration);
//        if(items.size() > 0)
        checkRefresh();
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
//        int i = 0;
//        SharedPreferences sPreferences = context.getSharedPreferences("taskList", Context.MODE_PRIVATE);
//        Set<String> unreadTaskSet;
//        if(sPreferences.contains("unreadTaskSet")) {
//            unreadTaskSet = sPreferences.getStringSet("unreadTaskSet", new HashSet<String>());
//        }else {
//            unreadTaskSet = new HashSet<>();
//        }
//        for(String jobId: unreadTaskSet){
//            myAbstractRecyclerViewAdapter.notifyItemChanged(i);
//            i++;
//        }
    }

    public void checkRefresh() {
        handler.postDelayed(mRunnable, Global.POLLING_INTERVAL);
//        loadService = LoadSir.getDefault().register(recyclerView, (Callback.OnReloadListener) v -> {
//
//        });
        for (Abstract item : items) {
            // 只查询处理中的任务进度
            String msg = item.getMessage();
//            if (msg.equals(Status.CREATED.toString()) || msg.equals("")) {
            new Thread(() -> {
                try {
                    new GetTask(getTask, item.getJobId()).send();
                } catch (Exception e) {
                    Log.e("exception", e.toString());
                }
            }).start();
//            }
        }
    }

    //using sharedPreference
    /*public void refresh(String jobId, String message, String result){
        int i = 0;
        for(DummyContent.DummyItem item : items){
            if(item.jobId.equals(jobId) && !item.message.equals(message)){
                if(item.message.equals("完成")){
                    item.result = result;
                }
                item.message = message;
                myAbstractRecyclerViewAdapter.notifyItemChanged(i);
            }
            i++;
        }
    }*/

    public void refresh(String jobId, String message, String result, String status, String title) {
        int i = 0;
        for (Abstract item : items) {
            if (item.getJobId().equals(jobId) && (!item.message.equals(message) || !item.getTitle().equals(title))) {
                Abstract mAbstract = AbstractsManager.getIntance(context).abstractsDao().loadByJobId(jobId);
                mAbstract.setMessage(message);
                mAbstract.setStatus(status);
                mAbstract.setTitle(title);
                if (message.equals(Status.DONE.toString())) {
                    mActivity.runOnUiThread(() -> new AlertDialog.Builder(mActivity)
                            .setTitle("提示")
                            .setMessage("摘要任务已完成！")
                            .setPositiveButton("确定", null)
                            .show());
                    mAbstract.setResult(result);
                }
                AbstractsManager.getIntance(context).abstractsDao().update(mAbstract);
                items.set(i, mAbstract);
                myAbstractRecyclerViewAdapter.notifyItemChanged(i);
            }
            i++;
        }
    }

    private void showDetailedAbstract(int position) {
        Intent intent = new Intent(getContext(), ShowAbstract.class);
        Abstract item = items.get(position);
        //using sharedPreference
        //TaskListSharedPreference.changeTaskToREAD(context, item.jobId);
        intent.putExtra("jobId", item.getJobId());
        item.setType("已读");
        AbstractsManager.getIntance(context).abstractsDao().update(item);
        items.remove(position);
        myAbstractRecyclerViewAdapter.notifyItemRemoved(position);
        startActivity(intent);
    }

    public static UnreadFragment getInstance(int index) {
        UnreadFragment f = new UnreadFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, index);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //保存activity引用

        if (context instanceof Activity) {
            this.mActivity = (Activity) context;
        }
    }
}
