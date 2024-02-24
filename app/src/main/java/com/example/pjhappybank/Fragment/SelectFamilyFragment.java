package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pjhappybank.R;

public class SelectFamilyFragment extends Fragment {

    public SelectFamilyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_family, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.tv_create_family).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFamilyFragment addFamilyFragment = new AddFamilyFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addFamilyFragment)
                        .commit();
            }
        });
        view.findViewById(R.id.tv_join_family).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinFamilyFragment addFamilyFragment = new JoinFamilyFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addFamilyFragment)
                        .commit();
            }
        });
    }
}
