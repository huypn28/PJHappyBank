package com.example.pjhappybank.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.pjhappybank.Model.Family;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddFamilyViewModel extends ViewModel{
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public AddFamilyViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }
    public void addFamilyData(String name, String code) {
        // Tham chiếu đến collection "family" trong Firestore
        CollectionReference familyRef = firestore.collection("family");

        // Tạo một đối tượng FamilyModel để đại diện cho dữ liệu cần thêm vào
        Family familyModel = new Family(name, code);

        // Thêm dữ liệu vào collection "family" trong Firestore
        familyRef.add(familyModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            // Dữ liệu đã được thêm thành công
                        } else {
                            // Xảy ra lỗi khi thêm dữ liệu
                        }
                    }
                });
    }
}
