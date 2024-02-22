package com.example.pjhappybank.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public RegisterViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void registerUserWithEmailAndPassword(String email, String password, String userName, OnRegisterListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = firebaseAuth.getCurrentUser().getUid();

                        saveUserToFirestore(userId, userName);

                        listener.onRegisterSuccess();
                    } else {
                        listener.onRegisterFailure(task.getException().getMessage());
                    }
                });
    }

    private void saveUserToFirestore(String userId, String userName) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", userId);
        user.put("name", userName);

        firestore.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                });
    }

    public interface OnRegisterListener {
        void onRegisterSuccess();

        void onRegisterFailure(String errorMessage);
    }
}
