package com.example.pjhappybank.ViewModel;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositiveViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public PositiveViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void updateHeartAndDate(String userId, int totalQuantity, OnUpdateListener listener) {
        updateHeartAndDateInUserCollection(userId, totalQuantity, listener);
    }

    private void updateHeartAndDateInUserCollection(String userId, int totalQuantity, OnUpdateListener listener) {
        firestore.collection("users").document(userId)
                .collection("heart")
                .whereEqualTo("date", new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.ENGLISH).format(new Date()))
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);

                            int currentQuantity = Integer.parseInt(documentSnapshot.getString("quantity"));
                            int newQuantity = currentQuantity + totalQuantity;

                            documentSnapshot.getReference().update("quantity", String.valueOf(newQuantity))
                                    .addOnSuccessListener(aVoid -> listener.onUpdateSuccess())
                                    .addOnFailureListener(e -> listener.onUpdateFailure(e.getMessage()));
                        }
                    }
                });
    }

    public interface OnUpdateListener {
        void onUpdateSuccess();

        void onUpdateFailure(String errorMessage);
    }
}
