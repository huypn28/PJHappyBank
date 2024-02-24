package com.example.pjhappybank.ViewModel;

import android.app.Activity;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public LoginViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void loginUserWithEmailAndPassword(String email, String password, final OnLoginListener listener) {
        if (email.isEmpty() || password.isEmpty()) {
            listener.onLoginFailure("Hãy nhập đầy đủ thông tin");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onLoginSuccess();
                    } else {
                        if (task.getException() != null &&
                                (task.getException().getMessage().contains("invalid email") ||
                                        task.getException().getMessage().contains("wrong password"))) {
                            listener.onLoginFailure("Thông tin chưa chính xác, vui lòng nhập lại");
                        } else {
                            listener.onLoginFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    public void getUserPosition(String userId, OnGetPositionListener listener) {
        DocumentReference userRef = firestore.collection("users").document(userId);

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String position = document.getString("position");
                    listener.onGetPositionSuccess(position);
                } else {
                    listener.onGetPositionFailure("User document does not exist");
                }
            } else {
                listener.onGetPositionFailure(task.getException().getMessage());
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

    public interface OnGetPositionListener {
        void onGetPositionSuccess(String position);
        void onGetPositionFailure(String errorMessage);
    }
}
