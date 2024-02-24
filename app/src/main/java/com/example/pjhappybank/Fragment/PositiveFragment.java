package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pjhappybank.Adapter.OpinionAdapter;
import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.PositiveViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PositiveFragment extends Fragment {

    private RecyclerView recyclerView;
    private OpinionAdapter adapter;
    private Set<Integer> selectedItems = new HashSet<>();
    private PositiveViewModel positiveViewModel;
    private FirebaseAuth firebaseAuth;

    public PositiveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_positive, container, false);

        recyclerView = view.findViewById(R.id.rcv_fragment_positive);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ImageView buttonImageView = view.findViewById(R.id.button_fragment_positive);
        firebaseAuth = FirebaseAuth.getInstance();

        String[] itemListArray = getResources().getStringArray(R.array.positive_items);
        List<String> itemList = Arrays.asList(itemListArray);

        positiveViewModel = new PositiveViewModel();

        adapter = new OpinionAdapter(itemList, new OpinionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                handleItemClick(position);
            }

            @Override
            public boolean isItemSelected(int position) {
                return selectedItems.contains(position);
            }
        });

        recyclerView.setAdapter(adapter);

        buttonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalQuantity = calculateTotalQuantity();

                positiveViewModel.updateHeartAndDate(
                        firebaseAuth.getCurrentUser().getUid(),
                        totalQuantity,
                        new PositiveViewModel.OnUpdateListener() {
                            @Override
                            public void onUpdateSuccess() {
                                showCongratulationFragment(totalQuantity);
                            }

                            @Override
                            public void onUpdateFailure(String errorMessage) {
                            }
                        }
                );

                selectedItems.clear();
            }
        });
        return view;
    }

    private void handleItemClick(int position) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position);
        } else {
            selectedItems.add(position);
        }

        adapter.notifyDataSetChanged();
    }

    private int calculateTotalQuantity() {
        int totalQuantity = selectedItems.size() * 20;
        return totalQuantity;
    }

    private void showCongratulationFragment(int totalQuantity) {
        CongratulationFragment congratulationFragment = new CongratulationFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("quantity", totalQuantity);
        congratulationFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, congratulationFragment)
                .addToBackStack(null)
                .commit();
    }
}
