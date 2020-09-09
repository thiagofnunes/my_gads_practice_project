package com.example.my_aad_practice_project.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.my_aad_practice_project.R;
import com.example.my_aad_practice_project.model.Data;

import java.util.ArrayList;


public class LearningLeadersRecyclerViewAdapter extends RecyclerView.Adapter<LearningLeadersRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Data>  mValues;

    public LearningLeadersRecyclerViewAdapter(ArrayList<Data> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.learning_leaders_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        String format = "%s learning hours, %s";
        format = String.format(format,mValues.get(position).getHours(),mValues.get(position).getCountry());
        holder.mContentView.setText(format);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Data mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.textViewName);
            mContentView = (TextView) view.findViewById(R.id.textViewVersion);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}