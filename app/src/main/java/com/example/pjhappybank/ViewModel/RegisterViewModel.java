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
        if (email.isEmpty() || password.isEmpty() || userName.isEmpty()) {
            listener.onRegisterFailure("Hãy nhập đầy đủ thông tin");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = firebaseAuth.getCurrentUser().getUid();

                        saveUserToFirestore(userId, userName);

                        createHeartCollection(userId);

                        listener.onRegisterSuccess();
                    } else {
                        if (task.getException() != null &&
                                task.getException().getMessage().contains("already in use")) {
                            listener.onRegisterFailure("Email đã tồn tại, vui lòng chọn email khác");
                        } else {
                            listener.onRegisterFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    private void saveUserToFirestore(String userId, String userName) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", userId);
        user.put("name", userName);
        user.put("emotion", null);
        user.put("position", null);

        firestore.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                });
    }

    private void createHeartCollection(String userId) {
        Map<String, Object> heartData = new HashMap<>();
        heartData.put("quantity", "0");

        String currentDate = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.ENGLISH).format(new java.util.Date());
        heartData.put("date", currentDate);

        firestore.collection("users").document(userId)
                .collection("heart").document()
                .set(heartData)
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
