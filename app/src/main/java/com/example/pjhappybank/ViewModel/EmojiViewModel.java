package com.example.pjhappybank.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class EmojiViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public EmojiViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void saveEmojiToFirestore(String emoji, OnEmojiSaveListener onEmojiSaveListener) {
        String userId = firebaseAuth.getCurrentUser().getUid();

        // Fetch the existing user data
        firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Update only the emotion field
                        Map<String, Object> userUpdate = new HashMap<>();
                        userUpdate.put("emotion", emoji);

                        // Merge the update with existing data
                        firestore.collection("users").document(userId)
                                .set(userUpdate, SetOptions.merge())
                                .addOnSuccessListener(aVoid -> {
                                    onEmojiSaveListener.onEmojiSaveSuccess();
                                })
                                .addOnFailureListener(e -> {
                                    onEmojiSaveListener.onEmojiSaveFailure(e.getMessage());
                                });
                    } else {
                        // Handle the case where the user document doesn't exist
                        onEmojiSaveListener.onEmojiSaveFailure("User document not found");
                    }
                })
                .addOnFailureListener(e -> {
                    onEmojiSaveListener.onEmojiSaveFailure(e.getMessage());
                });
    }

    public interface OnEmojiSaveListener {
        void onEmojiSaveSuccess();

        void onEmojiSaveFailure(String errorMessage);
    }
}

