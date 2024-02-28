package com.example.pjhappybank.Fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.pjhappybank.Model.Family;
import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.AddFamilyViewModel;
import com.example.pjhappybank.ViewModel.JoinFamilyViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class JoinFamilyFragment extends Fragment {
    private JoinFamilyViewModel joinFamilyViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_join_family, container, false);

        // Initialize ViewModel
        joinFamilyViewModel = new ViewModelProvider(this).get(JoinFamilyViewModel.class);

        // Find views
        EditText etFamilyCode = rootView.findViewById(R.id.tv_code_family);
        TextView tvJoin = rootView.findViewById(R.id.tv_join);

        // Set onClickListener for the "Tham gia" button
        tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String familyCode = etFamilyCode.getText().toString().trim();

                if (!familyCode.isEmpty()) {
                    joinFamilyViewModel.joinFamily(familyCode);
                    navigateBackToFamilyFragment();
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập mã gia đình", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
    private void navigateBackToFamilyFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FamilyFragment familyFragment = new FamilyFragment();
        fragmentTransaction.replace(R.id.fragment_container, familyFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
