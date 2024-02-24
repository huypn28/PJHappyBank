package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    private EditText userNameEditText, emailEditText, passwordEditText;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        registerViewModel = new RegisterViewModel();

        userNameEditText = view.findViewById(R.id.userName_register);
        emailEditText = view.findViewById(R.id.email_register);
        passwordEditText = view.findViewById(R.id.password_register);

        TextView registerButton = view.findViewById(R.id.bt_register_fragment_register);
        TextView haveAnAccountTextView = view.findViewById(R.id.haveAnAccount_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = userNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                registerViewModel.registerUserWithEmailAndPassword(email, password, userName, new RegisterViewModel.OnRegisterListener() {
                    @Override
                    public void onRegisterSuccess() {
                        Toast.makeText(v.getContext(), "Đã đăng kí thành công, vui lòng đăng nhập", Toast.LENGTH_SHORT).show();

                        switchToLoginFragment();
                    }

                    @Override
                    public void onRegisterFailure(String errorMessage) {
                    }
                });
            }
        });


        haveAnAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLoginFragment();
            }
        });

        return view;
    }
    private void switchToLoginFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LoginFragment());
        fragmentTransaction.commit();
    }
}
