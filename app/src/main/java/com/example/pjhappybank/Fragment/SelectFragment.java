package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.SelectViewModel;

public class SelectFragment extends Fragment {

    private SelectViewModel selectViewModel;
    private String selectedPosition = "";

    public SelectFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_person, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectViewModel = new SelectViewModel();

        ImageView daughter = view.findViewById(R.id.daughter_fragment_select_person);
        ImageView son = view.findViewById(R.id.son_fragment_select_person);
        ImageView mother = view.findViewById(R.id.mother_fragment_select_person);
        ImageView father = view.findViewById(R.id.father_fragment_select_person);

        daughter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePositionSelection("daughter");
            }
        });

        son.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePositionSelection("son");
            }
        });

        mother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePositionSelection("mother");
            }
        });

        father.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePositionSelection("father");
            }
        });

        TextView selectImageView = view.findViewById(R.id.option_register_fragment_select_person);
        selectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition.isEmpty()) {
                    showPositionSelectionPrompt();
                } else {
                    saveSelectedPositionToFirestore();
                    switchToMainFragment();
                }
            }
        });
    }

    private void handlePositionSelection(String position) {
        selectedPosition = position;
    }

    private void saveSelectedPositionToFirestore() {
        if (!selectedPosition.isEmpty()) {
            selectViewModel.savePositionToFirestore(selectedPosition, new SelectViewModel.OnPositionSaveListener() {
                @Override
                public void onPositionSaveSuccess() {
                }

                @Override
                public void onPositionSaveFailure(String errorMessage) {
                }
            });
        } else {
        }
    }

    private void switchToMainFragment() {
        MainFragment mainFragment = new MainFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mainFragment)
                .commit();
    }

    private void showPositionSelectionPrompt() {
        Toast.makeText(requireContext(), "Mời chọn vai trò trong gia đình", Toast.LENGTH_SHORT).show();
    }
}
