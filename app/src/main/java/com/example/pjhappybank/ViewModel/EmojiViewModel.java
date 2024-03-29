package com.example.pjhappybank.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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

        firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> userUpdate = new HashMap<>();
                        userUpdate.put("emotion", emoji);

                        firestore.collection("users").document(userId)
                                .update(userUpdate)
                                .addOnSuccessListener(aVoid -> {
                                    onEmojiSaveListener.onEmojiSaveSuccess();
                                })
                                .addOnFailureListener(e -> {
                                    onEmojiSaveListener.onEmojiSaveFailure(e.getMessage());
                                });
                    } else {
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


