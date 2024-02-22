package com.example.pjhappybank.ViewModel;

import android.app.Activity;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;

    public LoginViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loginUserWithEmailAndPassword(String email, String password, final OnLoginListener listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onLoginSuccess();
                    } else {
                        listener.onLoginFailure(task.getException().getMessage());
                    }
                });
    }


    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public interface OnLoginListener {
        void onLoginSuccess();
        void onLoginFailure(String errorMessage);
    }
}
