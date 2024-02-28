package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.ProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private TextView nameTextView;
    private TextView emailTextView;
    private ImageView profileImageView;
    private ProfileViewModel profileViewModel;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTextView = view.findViewById(R.id.username_fragment_profile);
        emailTextView = view.findViewById(R.id.email_fragment_profile);
        profileImageView = view.findViewById(R.id.emoji_fragment_profile);

        profileViewModel = new ProfileViewModel();

        profileViewModel.getUserInfo(new ProfileViewModel.OnUserInfoListener() {
            @Override
            public void onUserInfoSuccess(String name, String position, String email) {
                nameTextView.setText(name);
                emailTextView.setText(email);

                if ("daughter".equals(position)) {
                    profileImageView.setImageResource(R.drawable.daughter);
                }else if ("son".equals(position)) {
                    profileImageView.setImageResource(R.drawable.son);
                }else if ("mother".equals(position)) {
                    profileImageView.setImageResource(R.drawable.mother);
                }else if ("father".equals(position)) {
                    profileImageView.setImageResource(R.drawable.father);
                }else {
                    profileImageView.setImageResource(R.drawable.normal);
                }
            }

            @Override
            public void onUserInfoFailure(String errorMessage) {
            }
        });

        view.findViewById(R.id.bt_logout_profile).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                profileViewModel.logout(new ProfileViewModel.OnLogoutListener() {
                    @Override
                    public void onLogoutSuccess() {
                        switchToLoginFragment();
                    }

                    @Override
                    public void onLogoutFailure(String errorMessage) {
                        // Xử lý lỗi khi đăng xuất nếu cần
                        Log.e("ProfileFragment", "Đăng xuất thất bại: " + errorMessage);
                    }
                });
            }
        });


        BottomNavigationView bottomNavigationView = view.findViewById(R.id.layout_bottom_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    item.setChecked(true);
                    switchToMainFragment();
                    return true;

                default:
                    return false;
            }
        });
    }
    private void switchToMainFragment() {
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mainFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void switchToLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, loginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

