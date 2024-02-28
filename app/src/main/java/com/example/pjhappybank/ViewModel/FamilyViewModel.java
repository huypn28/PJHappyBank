package com.example.pjhappybank.ViewModel;

import android.util.Log;

import com.example.pjhappybank.Model.UserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FamilyViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public FamilyViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void getFamilyInfo(OnFamilyInfoListener listener) {
        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        firestore.collection("users")
                .document(currentUserId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String familyCode = documentSnapshot.getString("familyCode");

                    if (familyCode != null) {
                        getFamilyDetails(familyCode, listener);
                    } else {
                        listener.onFamilyInfoFailure("Chưa có familyCode");
                    }
                })
                .addOnFailureListener(e -> {
                    listener.onFamilyInfoFailure("Error fetching user details: " + e.getMessage());
                });
    }

    private void getFamilyDetails(String familyCode, OnFamilyInfoListener listener) {
        firestore.collection("family")
                .whereEqualTo("code", familyCode)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<UserDetails> userDetailsList = new ArrayList<>();

                        Query usersQuery = firestore.collection("users").whereEqualTo("familyCode", familyCode);

                        usersQuery.get()
                                .addOnSuccessListener(userQueryDocumentSnapshots -> {
                                    for (QueryDocumentSnapshot userSnapshot : userQueryDocumentSnapshots) {
                                        String name = userSnapshot.getString("name");
                                        String position = userSnapshot.getString("position");
                                        String emotion = userSnapshot.getString("emotion");

                                        // Retrieve all documents from the "heart" subcollection
                                        firestore.collection("users")
                                                .document(userSnapshot.getId())
                                                .collection("heart")
                                                .get()
                                                .addOnSuccessListener(heartQuerySnapshot -> {
                                                    for (QueryDocumentSnapshot heartSnapshot : heartQuerySnapshot) {
                                                        String quantity = heartSnapshot.getString("quantity");
                                                        userDetailsList.add(new UserDetails(name, position, emotion, quantity));
                                                    }

                                                    if (userDetailsList.size() == userQueryDocumentSnapshots.size()) {
                                                        listener.onFamilyInfoSuccess(userDetailsList, familyCode);
                                                        Log.d("FamilyViewModel", "onFamilyInfoSuccess: " + userDetailsList.size() + " members");
                                                    }
                                                })
                                                .addOnFailureListener(e -> {
                                                    listener.onFamilyInfoFailure("Lỗi khi lấy thông tin trái tim: " + e.getMessage());
                                                });
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    listener.onFamilyInfoFailure("Lỗi khi lấy thông tin thành viên gia đình: " + e.getMessage());
                                });
                    } else {
                        listener.onFamilyInfoFailure("Không tìm thấy gia đình với familyCode này");
                    }
                })
                .addOnFailureListener(e -> {
                    listener.onFamilyInfoFailure("Lỗi khi lấy thông tin gia đình: " + e.getMessage());
                });
    }




    public interface OnFamilyInfoListener {
        void onFamilyInfoSuccess(List<UserDetails> userDetailsList, String familyCode);

        void onFamilyInfoFailure(String errorMessage);
    }
}
