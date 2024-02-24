package com.example.pjhappybank.ViewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.pjhappybank.Model.Heart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public MainViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    // Phương thức để lấy tên từ Cloud Firestore và gán vào TextView
    public void loadUsername(OnDataLoadedListener<String> listener) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            firestore.collection("users")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                String username = document.getString("name");
                                listener.onDataLoaded(username);
                            } else {
                                Log.d("MainViewModel", "No such document");
                            }
                        } else {
                            Log.d("MainViewModel", "get failed with ", task.getException());
                        }
                    });
        } else {
        }
    }

    // Phương thức để lấy quantity và date từ Cloud Firestore và gán vào TextView
    public void loadHeartData(OnDataLoadedListener<Heart> listener) {
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
                                    Log.d("MainViewModel", "No such document");
                                }
                            }
                        } else {
                            Log.d("MainViewModel", "get failed with ", task.getException());
                        }
                    });
        } else {
        }
    }

    public interface OnDataLoadedListener<T> {
        void onDataLoaded(T data);
    }
}
