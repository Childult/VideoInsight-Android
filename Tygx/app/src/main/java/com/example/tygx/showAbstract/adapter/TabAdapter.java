package com.example.tygx.showAbstract.adapter;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tygx.databinding.FragmentSubtitleBinding;
import com.example.tygx.showAbstract.fragment.OnlyTextFragment;
import com.example.tygx.showAbstract.fragment.PicTextFragment;
import com.example.tygx.showAbstract.fragment.SubsectionFragment;
import com.example.tygx.showAbstract.fragment.SubtitleFragment;

//摘要内容适配器
public class TabAdapter extends FragmentStateAdapter {

    private static final int PIC_TEXT = 0;
    private static final int ONLY_TEXT = 1;
    private static final int SUBSECTION = 2;
    private static final int SUBTITLE = 3;

    SparseArray<Fragment> fragments = new SparseArray<>();
    int mNumOfTabs;

    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.put(PIC_TEXT, PicTextFragment.getInstance(0));
        fragments.put(ONLY_TEXT, OnlyTextFragment.getInstance(1));
        fragments.put(SUBSECTION, SubsectionFragment.getInstance(2));
        fragments.put(SUBTITLE, SubtitleFragment.getInstance(3));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case PIC_TEXT:
                if(fragments.get(PIC_TEXT) == null) {
                    fragment = PicTextFragment.getInstance(0);
                    fragments.put(PIC_TEXT, fragment);
                } else {
                    fragment = fragments.get(PIC_TEXT);
                }
                break;
            case ONLY_TEXT:
                if(fragments.get(ONLY_TEXT) == null) {
                    fragment = PicTextFragment.getInstance(0);
                    fragments.put(ONLY_TEXT, fragment);
                } else {
                    fragment = fragments.get(ONLY_TEXT);
                }
                break;
            case SUBSECTION:
                if(fragments.get(SUBSECTION) == null) {
                    fragment = PicTextFragment.getInstance(0);
                    fragments.put(SUBSECTION, fragment);
                } else {
                    fragment = fragments.get(SUBSECTION);
                }
                break;
            case SUBTITLE:
                if(fragments.get(SUBTITLE) == null) {
                    fragment = PicTextFragment.getInstance(0);
                    fragments.put(SUBTITLE, fragment);
                } else {
                    fragment = fragments.get(SUBTITLE);
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
