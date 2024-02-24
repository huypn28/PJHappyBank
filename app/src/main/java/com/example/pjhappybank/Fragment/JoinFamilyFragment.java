package com.example.pjhappybank.Fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pjhappybank.Model.Family;
import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.AddFamilyViewModel;
import com.example.pjhappybank.ViewModel.JoinFamilyViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class JoinFamilyFragment extends Fragment {
    private TextView tvJoin;
    private EditText tvCode;

    private JoinFamilyViewModel joinFamilyViewModel;
    private FirebaseAuth firebaseAuth;

    public JoinFamilyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_family, container, false);
        joinFamilyViewModel = new ViewModelProvider(this).get(JoinFamilyViewModel.class);
        tvJoin = view.findViewById(R.id.tv_join);
        tvCode = view.findViewById(R.id.tv_code_family);
        firebaseAuth = FirebaseAuth.getInstance();
        joinFamilyViewModel.getAllFamilyData();
        List<Family> familyList = joinFamilyViewModel.getFamilyList();

        tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < familyList.size(); i++) {
                    if (familyList.get(i).getcode().equals(tvCode.getText().toString())) {
                        joinFamilyViewModel.addFamilyData(firebaseAuth.getUid(), tvCode.getText().toString());
                        getActivity().onBackPressed();
                    }
                }

            }
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
