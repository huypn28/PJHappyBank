package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.HomeViewModel;

public class HomeFragment extends Fragment {

    private TextView usernameTextView;
    private TextView quantityHeartTextView;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        usernameTextView = view.findViewById(R.id.username_homeFragment);
        quantityHeartTextView = view.findViewById(R.id.quantity_heart_homeFragment);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.loadUsername(username -> {
            usernameTextView.setText(username);
        });

        homeViewModel.loadHeartData(heart -> {
            quantityHeartTextView.setText(heart.getQuantity());
        });

        return view;
    }
}
