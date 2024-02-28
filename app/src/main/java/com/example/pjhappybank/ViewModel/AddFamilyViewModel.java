package com.example.pjhappybank.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.pjhappybank.Model.Family;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddFamilyViewModel extends ViewModel {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public AddFamilyViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void addFamilyData(String name, String code) {
        CollectionReference familyRef = firestore.collection("family");

        Family familyModel = new Family(name, code);

        familyRef.add(familyModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(Task<DocumentReference> task) {
                        if (task.isSuccessful()) {

                            String familyId = task.getResult().getId();

                            // Cập nhật familyCode cho user
                            updateFamilyCodeForUser(familyId, code);
                        } else {
                            // Xảy ra lỗi khi thêm dữ liệu
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
