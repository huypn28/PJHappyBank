package com.example.pjhappybank.ViewModel;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NegativeViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public NegativeViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void updateHeartAndDate(String userId, int totalQuantity, OnUpdateListener listener) {
        updateHeartAndDateInUserCollection(userId, totalQuantity, listener);
    }

    private void updateHeartAndDateInUserCollection(String userId, int totalQuantity, OnUpdateListener listener) {
        String currentDate = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.ENGLISH).format(new Date());
        String currentMonth = new java.text.SimpleDateFormat("MM", java.util.Locale.ENGLISH).format(new Date());

        // Kiểm tra dữ liệu cho ngày hôm nay
        firestore.collection("users").document(userId)
                .collection("heart")
                .whereEqualTo("date", currentDate)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();

                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            // Có dữ liệu cho ngày hôm nay
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            int currentQuantity = Integer.parseInt(documentSnapshot.getString("quantity"));
                            int newQuantity = currentQuantity - totalQuantity;

                            documentSnapshot.getReference().update("quantity", String.valueOf(newQuantity))
                                    .addOnSuccessListener(aVoid -> listener.onUpdateSuccess())
                                    .addOnFailureListener(e -> listener.onUpdateFailure(e.getMessage()));
                        } else {
                            // Không có dữ liệu cho ngày hôm nay, kiểm tra dữ liệu cho hôm qua
                            firestore.collection("users").document(userId)
                                    .collection("heart")
                                    .whereEqualTo("date", calculateYesterdayDate())
                                    .limit(1)
                                    .get()
                                    .addOnCompleteListener(yesterdayTask -> {
                                        if (yesterdayTask.isSuccessful()) {
                                            QuerySnapshot yesterdayQuerySnapshot = yesterdayTask.getResult();

                                            if (yesterdayQuerySnapshot != null && !yesterdayQuerySnapshot.isEmpty()) {
                                                // Có dữ liệu cho hôm qua, lấy quantity và cộng với totalQuantity
                                                DocumentSnapshot yesterdayDocumentSnapshot = yesterdayQuerySnapshot.getDocuments().get(0);
                                                int yesterdayQuantity = Integer.parseInt(yesterdayDocumentSnapshot.getString("quantity"));
                                                int newQuantity = yesterdayQuantity - totalQuantity;

                                                // Thêm mới dữ liệu cho ngày hôm nay
                                                Map<String, Object> newData = new HashMap<>();
                                                newData.put("date", currentDate);
                                                newData.put("date", currentMonth);
                                                newData.put("quantity", String.valueOf(newQuantity));

                                                firestore.collection("users").document(userId)
                                                        .collection("heart")
                                                        .add(newData)
                                                        .addOnSuccessListener(documentReference -> listener.onUpdateSuccess())
                                                        .addOnFailureListener(e -> listener.onUpdateFailure(e.getMessage()));
                                            } else {
                                                // Không có dữ liệu cho hôm qua, kiểm tra dữ liệu cho hôm kia
                                                firestore.collection("users").document(userId)
                                                        .collection("heart")
                                                        .whereEqualTo("date", calculateDayBeforeYesterdayDate())
                                                        .limit(1)
                                                        .get()
                                                        .addOnCompleteListener(dayBeforeYesterdayTask -> {
                                                            if (dayBeforeYesterdayTask.isSuccessful()) {
                                                                QuerySnapshot dayBeforeYesterdayQuerySnapshot = dayBeforeYesterdayTask.getResult();

                                                                if (dayBeforeYesterdayQuerySnapshot != null && !dayBeforeYesterdayQuerySnapshot.isEmpty()) {
                                                                    // Có dữ liệu cho hôm kia, lấy quantity và cộng với totalQuantity
                                                                    DocumentSnapshot dayBeforeYesterdayDocumentSnapshot = dayBeforeYesterdayQuerySnapshot.getDocuments().get(0);
                                                                    int dayBeforeYesterdayQuantity = Integer.parseInt(dayBeforeYesterdayDocumentSnapshot.getString("quantity"));
                                                                    int newQuantity = dayBeforeYesterdayQuantity - totalQuantity;

                                                                    // Thêm mới dữ liệu cho ngày hôm nay
                                                                    Map<String, Object> newData = new HashMap<>();
                                                                    newData.put("date", currentDate);
                                                                    newData.put("date", currentMonth);
                                                                    newData.put("quantity", String.valueOf(newQuantity));

                                                                    firestore.collection("users").document(userId)
                                                                            .collection("heart")
                                                                            .add(newData)
                                                                            .addOnSuccessListener(documentReference -> listener.onUpdateSuccess())
                                                                            .addOnFailureListener(e -> listener.onUpdateFailure(e.getMessage()));
                                                                } else {
                                                                    // Không có dữ liệu cho hôm kia, thêm mới dữ liệu cho ngày hôm nay với quantity là totalQuantity
                                                                    Map<String, Object> newData = new HashMap<>();
                                                                    newData.put("date", currentDate);
                                                                    newData.put("date", currentMonth);
                                                                    newData.put("quantity", String.valueOf(totalQuantity));

                                                                    firestore.collection("users").document(userId)
                                                                            .collection("heart")
                                                                            .add(newData)
                                                                            .addOnSuccessListener(documentReference -> listener.onUpdateSuccess())
                                                                            .addOnFailureListener(e -> listener.onUpdateFailure(e.getMessage()));
                                                                }
                                                            } else {
                                                                // Xử lý lỗi khi truy vấn cho hôm kia không thành công
                                                                listener.onUpdateFailure(dayBeforeYesterdayTask.getException().getMessage());
                                                            }
                                                        });
                                            }
                                        } else {
                                            // Xử lý lỗi khi truy vấn cho hôm qua không thành công
                                            listener.onUpdateFailure(yesterdayTask.getException().getMessage());
                                        }
                                    });
                        }
                    } else {
                        // Xử lý lỗi khi truy vấn cho hôm nay không thành công
                        listener.onUpdateFailure(task.getException().getMessage());
                    }
                });
    }

    // Hàm tính ngày hôm qua
    private String calculateYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.ENGLISH).format(calendar.getTime());
    }

    // Hàm tính ngày hôm kia
    private String calculateDayBeforeYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        return new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.ENGLISH).format(calendar.getTime());
    }


    public interface OnUpdateListener {
        void onUpdateSuccess();

        void onUpdateFailure(String errorMessage);
    }
}
