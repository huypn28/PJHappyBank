package com.example.pjhappybank.ViewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.pjhappybank.Model.Heart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

                                    // Kiểm tra nếu ngày trong document trùng với ngày hiện tại
                                    if (date.equals(getCurrentDate())) {
                                        Heart heart = new Heart(quantity, date);
                                        listener.onDataLoaded(heart);
                                        return; // Đã tìm thấy và gọi callback, thoát khỏi vòng lặp
                                    }
                                } else {
                                    Log.d("MainViewModel", "No such document");
                                }
                            }
                            // Nếu không tìm thấy dữ liệu cho ngày hiện tại, có thể thông báo hoặc xử lý tương ứng
                            Log.d("MainViewModel", "No data found for today");
                        } else {
                            Log.d("MainViewModel", "get failed with ", task.getException());
                        }
                    });
        } else {
            // Xử lý trường hợp currentUser là null
        }
    }
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());
    }




    public interface OnDataLoadedListener<T> {
        void onDataLoaded(T data);
    }
}
