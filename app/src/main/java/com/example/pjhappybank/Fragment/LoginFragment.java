package com.example.pjhappybank.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private EditText emailEditText, passwordEditText;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginViewModel = new LoginViewModel();

        emailEditText = view.findViewById(R.id.email_login);
        passwordEditText = view.findViewById(R.id.password_login);

        TextView loginButton = view.findViewById(R.id.bt_login_fragment_login);
        TextView forgetPasswordTextView = view.findViewById(R.id.forgetPassword_login);
        TextView dontHaveAnAccountTextView = view.findViewById(R.id.dontHaveAnAccount_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                loginViewModel.loginUserWithEmailAndPassword(email, password, new LoginViewModel.OnLoginListener() {
                    @Override
                    public void onLoginSuccess() {
                        switchToEmojiFragment();
                    }

                    @Override
                    public void onLoginFailure(String errorMessage) {
                    }
                });
            }
        });

        // Xử lý sự kiện khi người dùng nhấn quên mật khẩu
        forgetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào quên mật khẩu, có thể chuyển hướng màn hình hoặc thực hiện các hành động khác
            }
        });

        dontHaveAnAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToRegisterFragment();
            }
        });

        return view;
    }

    private void switchToEmojiFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new EmojiFragment());
        fragmentTransaction.commit();
    }

    private void switchToRegisterFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new RegisterFragment());
        fragmentTransaction.commit();
    }



}
