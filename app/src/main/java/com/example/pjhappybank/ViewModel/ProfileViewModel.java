package com.example.pjhappybank.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public ProfileViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void getUserInfo(OnUserInfoListener listener) {
        String userId = firebaseAuth.getCurrentUser().getUid();

        firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String position = documentSnapshot.getString("position");
                        String email = firebaseAuth.getCurrentUser().getEmail();

                        listener.onUserInfoSuccess(name, position, email);
                    } else {
                        listener.onUserInfoFailure("User not found in Firestore");
                    }
                })
                .addOnFailureListener(e -> {
                    listener.onUserInfoFailure(e.getMessage());
                });
    }

    public void changePassword(String newPassword, OnPasswordChangeListener listener) {
        firebaseAuth.getCurrentUser().updatePassword(newPassword)
                .addOnSuccessListener(aVoid -> {
                    listener.onPasswordChangeSuccess();
                })
                .addOnFailureListener(e -> {
                    listener.onPasswordChangeFailure(e.getMessage());
                });
    }


    public void logout(OnLogoutListener listener) {

        listener.onLogoutSuccess();
    }

    public interface OnLogoutListener {
        void onLogoutSuccess();
        void onLogoutFailure(String errorMessage);
    }


    public interface OnUserInfoListener {
        void onUserInfoSuccess(String name, String position, String email);

        void onUserInfoFailure(String errorMessage);
    }

    public interface OnPasswordChangeListener {
        void onPasswordChangeSuccess();

        void onPasswordChangeFailure(String errorMessage);
    }
}

