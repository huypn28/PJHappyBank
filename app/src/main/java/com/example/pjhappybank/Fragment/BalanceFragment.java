package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pjhappybank.Adapter.BalanceAdapter;
import com.example.pjhappybank.Model.MonthBalance;
import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.BalanceViewModel;

import java.util.List;

public class BalanceFragment extends Fragment {

    private BalanceViewModel balanceViewModel;
    private RecyclerView recyclerView;
    private BalanceAdapter adapter;
    private TextView nowBalanceTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance_fluctuation, container, false);

        nowBalanceTextView = view.findViewById(R.id.now_balance_fragment_balance);
        recyclerView = view.findViewById(R.id.rcv_fragment_balance);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        balanceViewModel = new ViewModelProvider(this).get(BalanceViewModel.class);
        balanceViewModel.loadHeartData(heart -> {
            nowBalanceTextView.setText(heart.getQuantity());
        });
        observeViewModel();

        return view;
    }

    private void observeViewModel() {
        balanceViewModel.getMonthBalances().observe(getViewLifecycleOwner(), monthBalances -> {
            updateUI(monthBalances);

        });
    }

    private void updateUI(List<MonthBalance> monthBalances) {
        adapter = new BalanceAdapter(monthBalances);
        recyclerView.setAdapter(adapter);



    }
}















//public class BalanceFragment extends Fragment {
//
//    private BalanceViewModel balanceViewModel;
//    private BalanceAdapter balanceAdapter;
//    private FirebaseAuth firebaseAuth;
//    private TextView quantityHeartTextView;
//
//
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_balance_fluctuation, container, false);
//
//        RecyclerView recyclerView = view.findViewById(R.id.rcv_fragment_balance);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        quantityHeartTextView = view.findViewById(R.id.now_balance_fragment_balance);
//
//        balanceAdapter = new BalanceAdapter(getContext(), null, null);
//        recyclerView.setAdapter(balanceAdapter);
//        firebaseAuth = FirebaseAuth.getInstance();
//        balanceViewModel = new ViewModelProvider(this).get(BalanceViewModel.class);
//
//        balanceViewModel.loadHeartData(heart -> {
//            quantityHeartTextView.setText(heart.getQuantity());
//        });
//
//        balanceViewModel = new ViewModelProvider(this).get(BalanceViewModel.class);
//        balanceViewModel.getAllDatesAndQuantitiesWithChanges(
//                firebaseAuth.getCurrentUser().getUid(),
//                new BalanceViewModel.OnDataFetchWithChangesListener() {
//                    @Override
//                    public void onDataFetchSuccess(List<Heart> dateQuantityList, List<Integer> quantityChangesList) {
//                        balanceAdapter.setDataListAndChanges(dateQuantityList, quantityChangesList);
//                        balanceAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onDataFetchFailure(String errorMessage) {
//                    }
//                }
//        );
//
//        return view;
//    }
//}
