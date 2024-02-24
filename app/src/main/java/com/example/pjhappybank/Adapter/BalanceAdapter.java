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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
            // Check if the date is today
            if (!isToday(data.getDate())) {
                holder.dateTextView.setText(data.getDate());
                holder.quantityTextView.setText(data.getQuantity());

                if (quantityChangesList != null && position < quantityChangesList.size()) {
                    int quantityChange = quantityChangesList.get(position);
                    holder.changeTextView.setText(String.valueOf(Math.abs(quantityChange))); // Use absolute value

                    if (quantityChange < 0) {
                        holder.changeTextView.setTextColor(context.getResources().getColor(R.color.red)); // Set red color for negative changes
                        holder.changeTextView.setText("-" + holder.changeTextView.getText()); // Add '-' sign for negative changes
                    } else {
                        holder.changeTextView.setTextColor(context.getResources().getColor(R.color.green)); // Set green color for positive changes
                        holder.changeTextView.setText("+" + holder.changeTextView.getText()); // Add '+' sign for positive changes
                    }
                }
            } else {
                // Hide the item for today
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }
    }

    private boolean isToday(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String today = sdf.format(Calendar.getInstance().getTime());
        return today.equals(date);
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
