package com.example.pjhappybank.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pjhappybank.Model.Heart;
import com.example.pjhappybank.Model.MonthBalance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BalanceViewModel extends ViewModel {

    private final MutableLiveData<List<MonthBalance>> monthBalances = new MutableLiveData<>();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public LiveData<List<MonthBalance>> getMonthBalances() {
        loadMonthBalances();
        return monthBalances;
    }

    private void loadMonthBalances() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            firestore.collection("users").document(userId)
                    .collection("heart")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<Heart> hearts = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Heart heart = document.toObject(Heart.class);
                            hearts.add(heart);
                        }
                        List<MonthBalance> monthBalancesList = calculateMonthBalances(hearts);
                        monthBalances.postValue(monthBalancesList);
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                    });
        } else {
            // Handle the case when the current user is null
        }
    }

    private List<MonthBalance> calculateMonthBalances(List<Heart> hearts) {
        Map<String, Heart> lastDayHearts = new HashMap<>();
        List<MonthBalance> monthBalancesList = new ArrayList<>();

        for (Heart heart : hearts) {
            try {
                Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(heart.getDate());
                String monthKey = new SimpleDateFormat("MM-yyyy", Locale.ENGLISH).format(date);

                if (!lastDayHearts.containsKey(monthKey) || isLaterMonth(date, lastDayHearts.get(monthKey).getDate())) {
                    lastDayHearts.put(monthKey, heart);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Calculate the change in quantity between consecutive months
        List<String> sortedMonths = new ArrayList<>(lastDayHearts.keySet());
        Collections.sort(sortedMonths);

        for (int i = 0; i < sortedMonths.size(); i++) {
            String currentMonthKey = sortedMonths.get(i);
            String nextMonthKey = sortedMonths.get((i + 1) % sortedMonths.size()); // Get the next month cyclically

            Heart currentHeart = lastDayHearts.get(currentMonthKey);
            Heart nextHeart = lastDayHearts.get(nextMonthKey);

            if (currentHeart != null && nextHeart != null) {
                int currentQuantity = Integer.parseInt(currentHeart.getQuantity());
                int nextQuantity = Integer.parseInt(nextHeart.getQuantity());
                int change = nextQuantity - currentQuantity;

                monthBalancesList.add(new MonthBalance(nextMonthKey, nextQuantity, change));
            }
        }

        // Sort the list by monthKey if needed

        return monthBalancesList;
    }



    // Helper method to check if the new date is in a later month
    private boolean isLaterMonth(Date newDate, String existingDateStr) {
        try {
            Date existingDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(existingDateStr);
            return newDate.after(existingDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
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

}




//public class BalanceViewModel extends ViewModel {
//
//    private FirebaseAuth firebaseAuth;
//    private FirebaseFirestore firestore;
//
//    public BalanceViewModel() {
//        firebaseAuth = FirebaseAuth.getInstance();
//        firestore = FirebaseFirestore.getInstance();
//    }
//    public void getAllDatesAndQuantitiesWithChanges(String userId, OnDataFetchWithChangesListener listener) {
//        firestore.collection("users").document(userId)
//                .collection("heart")
//                .orderBy("date")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful() && task.getResult() != null) {
//                        List<Heart> dateQuantityList = new ArrayList<>();
//                        List<Integer> quantityChangesList = new ArrayList<>();
//
//                        List<QueryDocumentSnapshot> documents = new ArrayList<>();
//                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
//                            documents.add((QueryDocumentSnapshot) document);
//                        }
//
//                        for (int i = 0; i < documents.size(); i++) {
//                            QueryDocumentSnapshot document = documents.get(i);
//
//                            String date = document.getString("date");
//                            String quantity = document.getString("quantity");
//
//                            dateQuantityList.add(new Heart(quantity, date));
//
//                            if (i > 0) {
//                                int previousQuantity = Integer.parseInt(documents.get(i - 1).getString("quantity"));
//                                int currentQuantity = Integer.parseInt(quantity);
//                                int quantityChange = currentQuantity - previousQuantity;
//
//                                quantityChangesList.add(quantityChange);
//                            }
//                        }
//
//                        listener.onDataFetchSuccess(dateQuantityList, quantityChangesList);
//                    } else {
//                        listener.onDataFetchFailure(task.getException().getMessage());
//                    }
//                });
//    }
//    public void loadHeartData(MainViewModel.OnDataLoadedListener<Heart> listener) {
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if (currentUser != null) {
//            String userId = currentUser.getUid();
//
//            firestore.collection("users")
//                    .document(userId)
//                    .collection("heart")
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                if (document.exists()) {
//                                    String quantity = document.getString("quantity");
//                                    String date = document.getString("date");
//
//                                    // Kiểm tra nếu ngày trong document trùng với ngày hiện tại
//                                    if (date.equals(getCurrentDate())) {
//                                        Heart heart = new Heart(quantity, date);
//                                        listener.onDataLoaded(heart);
//                                        return; // Đã tìm thấy và gọi callback, thoát khỏi vòng lặp
//                                    }
//                                } else {
//                                    Log.d("BalanceViewModel", "No such document");
//                                }
//                            }
//                            // Nếu không tìm thấy dữ liệu cho ngày hiện tại, có thể thông báo hoặc xử lý tương ứng
//                            Log.d("BalanceViewModel", "No data found for today");
//                        } else {
//                            Log.d("BalanceViewModel", "get failed with ", task.getException());
//                        }
//                    });
//        } else {
//        }
//    }
//    private String getCurrentDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        return sdf.format(new Date());
//    }
//
//
//
//    public interface OnDataFetchWithChangesListener {
//        void onDataFetchSuccess(List<Heart> dateQuantityList, List<Integer> quantityChangesList);
//
//        void onDataFetchFailure(String errorMessage);
//    }
//}
