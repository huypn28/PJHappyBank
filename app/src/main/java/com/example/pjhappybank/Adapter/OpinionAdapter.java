package com.example.pjhappybank.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pjhappybank.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OpinionAdapter extends RecyclerView.Adapter<OpinionAdapter.ViewHolder> {

    private List<String> itemList;
    private Set<Integer> selectedItems;
    private OnItemClickListener onItemClickListener;

    public OpinionAdapter(List<String> itemList, OnItemClickListener onItemClickListener) {
        this.itemList = itemList;
        this.onItemClickListener = onItemClickListener;
        this.selectedItems = new HashSet<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opinion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = itemList.get(position);
        holder.textView.setText(item);

        if (selectedItems.contains(position)) {
            holder.textView.setBackgroundResource(R.drawable.select_background);
        } else {
            holder.textView.setBackgroundResource(R.drawable.no_select_background);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);

                if (selectedItems.contains(position)) {
                    selectedItems.remove(position);
                } else {
                    selectedItems.add(position);
                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_opinion);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        boolean isItemSelected(int position);
    }
}
