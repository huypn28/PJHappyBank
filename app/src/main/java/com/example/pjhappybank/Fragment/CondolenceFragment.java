
package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pjhappybank.R;

public class CondolenceFragment extends Fragment {

    private TextView textViewQuantity;

    public CondolenceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_condolence, container, false);

        textViewQuantity = view.findViewById(R.id.minus_heart_fragment_condolence);

        if (getArguments() != null) {
            int quantity = getArguments().getInt("quantity", 0);
            updateQuantity(quantity);
        }
        ImageView nextButton = view.findViewById(R.id.next_fragment_condolence);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMainFragment();
            }
        });

        return view;
    }

    private void updateQuantity(int quantity) {
        textViewQuantity.setText(String.valueOf(quantity));
    }
    private void switchToMainFragment() {
        MainFragment maintiveFragment = new MainFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, maintiveFragment)
                .commit();
    }
}
