package com.example.tygx.myAbstract.adapter;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tygx.myAbstract.fragment.DealingFragment;
import com.example.tygx.myAbstract.fragment.DoneFragment;
import com.example.tygx.showAbstract.fragment.OnlyTextFragment;
import com.example.tygx.showAbstract.fragment.PicTextFragment;
import com.example.tygx.showAbstract.fragment.SubsectionFragment;
import com.example.tygx.showAbstract.fragment.SubtitleFragment;

//摘要处理情况栏适配器
public class MyAbstractAdapter extends FragmentStateAdapter {

    private static final int DONE = 0;
    private static final int DEALING = 1;

    SparseArray<Fragment> fragments = new SparseArray<>();
    int mNumOfTabs;

    public MyAbstractAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.put(DONE, DoneFragment.getInstance(1));
        fragments.put(DEALING, DealingFragment.getInstance(1));
    }

    //创建
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case DONE:
                if(fragments.get(DONE) == null) {
                    fragment = PicTextFragment.getInstance(1);
                    fragments.put(DONE, fragment);
                } else {
                    fragment = fragments.get(DONE);
                }
                break;
            case DEALING:
                if(fragments.get(DEALING) == null) {
                    fragment = PicTextFragment.getInstance(1);
                    fragments.put(DEALING, fragment);
                } else {
                    fragment = fragments.get(DEALING);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
