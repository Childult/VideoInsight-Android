package com.example.tygx.myAbstract.adapter;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tygx.myAbstract.fragment.UnreadFragment;
import com.example.tygx.myAbstract.fragment.ReadFragment;
import com.example.tygx.showAbstract.fragment.PicTextFragment;

//摘要处理情况栏适配器
public class MyAbstractAdapter extends FragmentStateAdapter {

    private static final int UNREAD = 0;
    private static final int READ = 1;

    SparseArray<Fragment> fragments = new SparseArray<>();
    int mNumOfTabs;

    public MyAbstractAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.put(UNREAD, UnreadFragment.getInstance(0));
        fragments.put(READ, ReadFragment.getInstance(1));
    }

    //创建
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case UNREAD:
                if(fragments.get(UNREAD) == null) {
                    fragment = UnreadFragment.getInstance(0);
                    fragments.put(UNREAD, fragment);
                } else {
                    fragment = fragments.get(UNREAD);
                }
                break;
            case READ:
                if(fragments.get(READ) == null) {
                    fragment = ReadFragment.getInstance(1);
                    fragments.put(READ, fragment);
                } else {
                    fragment = fragments.get(READ);
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
