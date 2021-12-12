package ru.mirea.chernyshev.mireaproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";

    private String[] internalDataSet;
    private LayoutInflater mInflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            //
            textView = view.findViewById(R.id.textViewHistory);
        }

        public TextView getRecyclerView() {
            return textView;
        }
    }

    public CustomAdapter(@NonNull Context context,@NonNull String[] dataSet) {
        internalDataSet = dataSet;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder call");
        holder.getRecyclerView().setText(internalDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return internalDataSet.length;
    }
}
