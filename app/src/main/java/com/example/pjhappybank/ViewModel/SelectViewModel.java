package com.example.pjhappybank.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SelectViewModel {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public SelectViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void savePositionToFirestore(String position, SelectViewModel.OnPositionSaveListener onPositionSaveListener) {
        String userId = firebaseAuth.getCurrentUser().getUid();

        firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> userUpdate = new HashMap<>();
                        userUpdate.put("position", position);

                        firestore.collection("users").document(userId)
                                .update(userUpdate)
                                .addOnSuccessListener(aVoid -> {
                                    onPositionSaveListener.onPositionSaveSuccess();
                                })
                                .addOnFailureListener(e -> {
                                    onPositionSaveListener.onPositionSaveFailure(e.getMessage());
                                });
                    } else {
                        onPositionSaveListener.onPositionSaveFailure("User document not found");
                    }
                })
                .addOnFailureListener(e -> {
                    onPositionSaveListener.onPositionSaveFailure(e.getMessage());
                });
    }

    public interface OnPositionSaveListener {
        void onPositionSaveSuccess();

        void onPositionSaveFailure(String errorMessage);
    }
}
