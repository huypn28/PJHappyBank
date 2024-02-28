package com.example.pjhappybank.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.pjhappybank.Model.Family;
import com.example.pjhappybank.Model.RelationShip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class JoinFamilyViewModel extends ViewModel {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public JoinFamilyViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void joinFamily(String familyCode) {
        CollectionReference familyRef = firestore.collection("family");

        familyRef.whereEqualTo("code", familyCode)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                String familyId = task.getResult().getDocuments().get(0).getId();

                                updateFamilyCodeForUser(familyId, familyCode);
                            } else {

                            }
                        } else {

                        }
                    }
                });
    }

    private void updateFamilyCodeForUser(String familyId, String code) {
        String userId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference userRef = firestore.collection("users").document(userId);

        userRef.update("familyCode", code)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                        } else {

                        }
                    }
                });
    }
}
