package com.example.my_aad_practice_project.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.my_aad_practice_project.fragments.LearningLeadersFragment;
import com.example.my_aad_practice_project.R;
import com.example.my_aad_practice_project.fragments.SkillIqFragment;
import com.example.my_aad_practice_project.model.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};

    private static  ArrayList<Data> skillIQlist;
    private static ArrayList<Data> learningLeaderslist;

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return LearningLeadersFragment.newInstance(learningLeaderslist);
        } else {
            return SkillIqFragment.newInstance(skillIQlist);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {

        return TAB_TITLES.length;
    }

    public  void setSkillIQlist(List<Data> skillIQlist) {
        ArrayList<Data> arrayList = new ArrayList<>(skillIQlist);
        SectionsPagerAdapter.skillIQlist = arrayList;
    }

    public  void setLearningLeaderslist(List<Data> learningLeaderslist) {
        ArrayList<Data> arrayList = new ArrayList<>(learningLeaderslist);
        SectionsPagerAdapter.learningLeaderslist = arrayList;
    }
}