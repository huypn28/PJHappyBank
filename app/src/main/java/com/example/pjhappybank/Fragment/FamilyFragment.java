package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pjhappybank.Adapter.FamilyAdapter;
import com.example.pjhappybank.Model.UserDetails;
import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.FamilyViewModel;

import java.util.List;

public class FamilyFragment extends Fragment {
    private TextView familyCodeTextView;

    private RecyclerView recyclerView;
    private FamilyAdapter familyAdapter;
    private FamilyViewModel familyViewModel;

    public FamilyFragment() {
        familyViewModel = new FamilyViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family, container, false);
        familyCodeTextView = view.findViewById(R.id.family_code_family_fragment);
        recyclerView = view.findViewById(R.id.rcv_fragmentfamily);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        familyAdapter = new FamilyAdapter(getActivity());
        recyclerView.setAdapter(familyAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.bt_add_fragment_family).setOnClickListener(v -> navigateToSelectFamilyFragment());

        loadFamilyInfo();
    }

    private void loadFamilyInfo() {
        familyViewModel.getFamilyInfo(new FamilyViewModel.OnFamilyInfoListener() {
            @Override
            public void onFamilyInfoSuccess(List<UserDetails> userDetailsList,String familyCode) {
                familyCodeTextView.setText(familyCode);

                familyAdapter.setFamilyMembers(userDetailsList);
            }

            @Override
            public void onFamilyInfoFailure(String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToSelectFamilyFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SelectFamilyFragment selectFamilyFragment = new SelectFamilyFragment();
        fragmentTransaction.replace(R.id.fragment_container, selectFamilyFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
