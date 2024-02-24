package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.EmojiViewModel;

public class FamilyFragment extends Fragment {

    public FamilyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_family, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SelectFamilyFragment selectFamilyFragment = new SelectFamilyFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectFamilyFragment)
                        .commit();
            }
        },2000);
    }
}
