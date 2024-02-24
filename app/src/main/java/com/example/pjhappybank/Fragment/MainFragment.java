package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.MainViewModel;

public class MainFragment extends Fragment {

    private TextView usernameTextView;
    private TextView quantityHeartTextView;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        usernameTextView = view.findViewById(R.id.username_homeFragment);
        quantityHeartTextView = view.findViewById(R.id.quantity_heart_homeFragment);
        ImageView diaryImageView = view.findViewById(R.id.diary_fragment_main);


        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.loadUsername(username -> {
            usernameTextView.setText(username);
        });

        mainViewModel.loadHeartData(heart -> {
            quantityHeartTextView.setText(heart.getQuantity());
        });
        diaryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToEmojiFragment();
            }
        });
        return view;


    }
    private void switchToEmojiFragment() {
        EmojiFragment emojiFragment = new EmojiFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, emojiFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
