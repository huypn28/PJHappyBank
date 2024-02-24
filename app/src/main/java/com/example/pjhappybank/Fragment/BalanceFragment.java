package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pjhappybank.Adapter.BalanceAdapter;
import com.example.pjhappybank.Model.Heart;
import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.BalanceViewModel;

import java.util.List;

public class BalanceFragment extends Fragment {

    private BalanceViewModel balanceViewModel;
    private BalanceAdapter balanceAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance_fluctuation, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rcv_fragment_balance);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        balanceAdapter = new BalanceAdapter(getContext(), null, null);
        recyclerView.setAdapter(balanceAdapter);

        balanceViewModel = new ViewModelProvider(this).get(BalanceViewModel.class);
        balanceViewModel.getAllDatesAndQuantitiesWithChanges("userId", new BalanceViewModel.OnDataFetchWithChangesListener() {
            @Override
            public void onDataFetchSuccess(List<Heart> dateQuantityList, List<Integer> quantityChangesList) {
                balanceAdapter.setDataListAndChanges(dateQuantityList, quantityChangesList);
                balanceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDataFetchFailure(String errorMessage) {
            }
        });

        return view;
    }
}
