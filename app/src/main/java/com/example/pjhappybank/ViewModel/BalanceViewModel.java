package com.example.pjhappybank.ViewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.pjhappybank.Model.Heart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BalanceViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public BalanceViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void getAllDatesAndQuantitiesWithChanges(String userId, OnDataFetchWithChangesListener listener) {
        firestore.collection("users").document(userId)
                .collection("heart")
                .orderBy("date")  // Sort the data by date in ascending order
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Heart> dateQuantityList = new ArrayList<>();
                        List<Integer> quantityChangesList = new ArrayList<>();

                        List<QueryDocumentSnapshot> documents = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            documents.add((QueryDocumentSnapshot) document);
                        }

                        for (int i = 0; i < documents.size(); i++) {
                            QueryDocumentSnapshot document = documents.get(i);

                            String date = document.getString("date");
                            String quantity = document.getString("quantity");

                            dateQuantityList.add(new Heart(quantity, date));

                            if (i > 0) {
                                int previousQuantity = Integer.parseInt(documents.get(i - 1).getString("quantity"));
                                int currentQuantity = Integer.parseInt(quantity);
                                int quantityChange = currentQuantity - previousQuantity;

                                quantityChangesList.add(quantityChange);
                            }
                        }

                        listener.onDataFetchSuccess(dateQuantityList, quantityChangesList);
                    } else {
                        listener.onDataFetchFailure(task.getException().getMessage());
                    }
                });
    }
    public void loadHeartData(MainViewModel.OnDataLoadedListener<Heart> listener) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            firestore.collection("users")
                    .document(userId)
                    .collection("heart")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    String quantity = document.getString("quantity");
                                    String date = document.getString("date");
                                    Heart heart = new Heart(quantity, date);
                                    listener.onDataLoaded(heart);
                                } else {
                                    Log.d("BalanceViewModel", "No such document");
                                }
                            }
                        } else {
                            Log.d("BalanceViewModel", "get failed with ", task.getException());
                        }
                    });
        } else {
        }
    }



    public interface OnDataFetchWithChangesListener {
        void onDataFetchSuccess(List<Heart> dateQuantityList, List<Integer> quantityChangesList);

        void onDataFetchFailure(String errorMessage);
    }
}
