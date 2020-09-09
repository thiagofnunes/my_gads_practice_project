package com.example.my_aad_practice_project.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.my_aad_practice_project.R;
import com.example.my_aad_practice_project.adapters.MySkillIqRecyclerViewAdapter;
import com.example.my_aad_practice_project.model.Data;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class SkillIqFragment extends Fragment {

    private static final String DATA_LIST = "data_list";
    private ArrayList<Data> dataList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SkillIqFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SkillIqFragment newInstance(ArrayList<Data> dataList) {
        SkillIqFragment fragment = new SkillIqFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA_LIST, dataList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.dataList = (ArrayList<Data>) getArguments().getSerializable(DATA_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill_iq_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new MySkillIqRecyclerViewAdapter(dataList));
        }
        return view;
    }
}