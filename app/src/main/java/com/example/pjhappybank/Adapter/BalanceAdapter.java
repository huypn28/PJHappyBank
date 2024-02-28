package com.example.pjhappybank.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pjhappybank.Model.MonthBalance;
import com.example.pjhappybank.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder> {

    private List<MonthBalance> monthBalances;

    public BalanceAdapter(List<MonthBalance> monthBalances) {
        this.monthBalances = monthBalances;
        // Sort the monthBalances list by year and month
        Collections.sort(monthBalances, new MonthBalanceComparator());
    }

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance, parent, false);
        return new BalanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {
        MonthBalance monthBalance = monthBalances.get(position);

        holder.monthTextView.setText(monthBalance.getMonthKey());

        int change = monthBalance.getQuantityChange();
        holder.changeTextView.setText((change >= 0 ? "+" : "") + change);

        // Set text color based on change value
        if (change < 0) {
            holder.changeTextView.setTextColor(Color.RED);
        } else {
            holder.changeTextView.setTextColor(Color.GREEN);
        }

        holder.balanceTextView.setText(String.valueOf(monthBalance.getTotalQuantity()));
    }

    @Override
    public int getItemCount() {
        return monthBalances.size();
    }

    static class BalanceViewHolder extends RecyclerView.ViewHolder {
        TextView monthTextView, changeTextView, balanceTextView;

        BalanceViewHolder(@NonNull View itemView) {
            super(itemView);
            monthTextView = itemView.findViewById(R.id.month_item_balance);
            changeTextView = itemView.findViewById(R.id.change_balance_item_balance);
            balanceTextView = itemView.findViewById(R.id.balance_item_balance);
        }
    }

    // Custom Comparator to sort MonthBalances by year and month
    private static class MonthBalanceComparator implements Comparator<MonthBalance> {
        @Override
        public int compare(MonthBalance mb1, MonthBalance mb2) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy", Locale.ENGLISH);
            try {
                Date date1 = sdf.parse(mb1.getMonthKey());
                Date date2 = sdf.parse(mb2.getMonthKey());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}


















//public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder> {
//
//    private Context context;
//    private List<Heart> dataList;
//    private List<Integer> quantityChangesList;
//
//    public BalanceAdapter(Context context, List<Heart> dataList, List<Integer> quantityChangesList) {
//        this.context = context;
//        this.dataList = dataList;
//        this.quantityChangesList = quantityChangesList;
//    }
//
//    @NonNull
//    @Override
//    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_balance, parent, false);
//        return new BalanceViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {
//        if (dataList != null && position < dataList.size()) {
//            Heart data = dataList.get(position);
//            // Check if the date is today
//            if (!isToday(data.getDate())) {
//                holder.dateTextView.setText(data.getDate());
//                holder.quantityTextView.setText(data.getQuantity());
//
//                if (quantityChangesList != null && position < quantityChangesList.size()) {
//                    int quantityChange = quantityChangesList.get(position);
//                    holder.changeTextView.setText(String.valueOf(Math.abs(quantityChange))); // Use absolute value
//
//                    if (quantityChange < 0) {
//                        holder.changeTextView.setTextColor(context.getResources().getColor(R.color.red)); // Set red color for negative changes
//                        holder.changeTextView.setText("-" + holder.changeTextView.getText()); // Add '-' sign for negative changes
//                    } else {
//                        holder.changeTextView.setTextColor(context.getResources().getColor(R.color.green)); // Set green color for positive changes
//                        holder.changeTextView.setText("+" + holder.changeTextView.getText()); // Add '+' sign for positive changes
//                    }
//                }
//            } else {
//                // Hide the item for today
//                holder.itemView.setVisibility(View.GONE);
//                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//            }
//        }
//    }
//
//    private boolean isToday(String date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//        String today = sdf.format(Calendar.getInstance().getTime());
//        return today.equals(date);
//    }
//
//    @Override
//    public int getItemCount() {
//        return Math.max(dataList != null ? dataList.size() : 0, quantityChangesList != null ? quantityChangesList.size() : 0);
//    }
//
//    static class BalanceViewHolder extends RecyclerView.ViewHolder {
//        TextView dateTextView, quantityTextView, changeTextView;
//
//        public BalanceViewHolder(@NonNull View itemView) {
//            super(itemView);
//            dateTextView = itemView.findViewById(R.id.day_item_balance);
//            quantityTextView = itemView.findViewById(R.id.balance_item_balance);
//            changeTextView = itemView.findViewById(R.id.change_balance_item_balance);
//        }
//    }
//
//    public void setDataListAndChanges(List<Heart> dataList, List<Integer> quantityChangesList) {
//        this.dataList = dataList;
//        this.quantityChangesList = quantityChangesList;
//        notifyDataSetChanged();
//    }
//}
