package com.example.pjhappybank.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pjhappybank.Model.Heart;
import com.example.pjhappybank.R;

import java.util.List;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder> {

    private Context context;
    private List<Heart> dataList;
    private List<Integer> quantityChangesList;

    public BalanceAdapter(Context context, List<Heart> dataList, List<Integer> quantityChangesList) {
        this.context = context;
        this.dataList = dataList;
        this.quantityChangesList = quantityChangesList;
    }

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_balance, parent, false);
        return new BalanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {
        if (dataList != null && position < dataList.size()) {
            Heart data = dataList.get(position);
            holder.dateTextView.setText(data.getDate());
            holder.quantityTextView.setText(data.getQuantity());
        }

        if (quantityChangesList != null && position < quantityChangesList.size()) {
            int quantityChange = quantityChangesList.get(position);
            holder.changeTextView.setText(String.valueOf(quantityChange));
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(dataList != null ? dataList.size() : 0, quantityChangesList != null ? quantityChangesList.size() : 0);
    }

    static class BalanceViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView, quantityTextView, changeTextView;

        public BalanceViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.day_item_balance);
            quantityTextView = itemView.findViewById(R.id.balance_item_balance);
            changeTextView = itemView.findViewById(R.id.change_balance_item_balance);
        }
    }

    public void setDataListAndChanges(List<Heart> dataList, List<Integer> quantityChangesList) {
        this.dataList = dataList;
        this.quantityChangesList = quantityChangesList;
        notifyDataSetChanged();
    }
}
