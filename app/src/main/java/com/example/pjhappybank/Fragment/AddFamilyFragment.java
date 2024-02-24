package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.AddFamilyViewModel;
import com.example.pjhappybank.ViewModel.MainViewModel;

import java.util.Random;

public class AddFamilyFragment extends Fragment {

    private TextView tvCode, tvCreate;
    private EditText tvName;

    private AddFamilyViewModel addFamilyViewModel;
    public AddFamilyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_family, container, false);
        addFamilyViewModel = new ViewModelProvider(this).get(AddFamilyViewModel.class);
        tvName = view.findViewById(R.id.tv_family_name);
        tvCode = view.findViewById(R.id.tv_code_family);
        tvCreate = view.findViewById(R.id.tv_create);
        String random =  generateRandomSixDigitNumber()+"";
        tvCode.setText(random);
        tvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFamilyViewModel.addFamilyData(tvName.getText().toString(), random);
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    public int generateRandomSixDigitNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // Số ngẫu nhiên từ 100000 đến 999999
    }

}
