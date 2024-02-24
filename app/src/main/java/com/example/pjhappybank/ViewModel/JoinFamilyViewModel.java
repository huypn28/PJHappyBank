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

    private List<Family> familyList;

    public List<Family> getFamilyList() {
        return familyList;
    }

    public JoinFamilyViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        familyList = new ArrayList<>();
    }
    public void addFamilyData(String user, String code) {
        // Tham chiếu đến collection "family" trong Firestore
        CollectionReference familyRef = firestore.collection("relationship");

        // Tạo một đối tượng FamilyModel để đại diện cho dữ liệu cần thêm vào
        RelationShip relationShip = new RelationShip(user, code);

        // Thêm dữ liệu vào collection "family" trong Firestore
        familyRef.add(relationShip)
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

    // Phương thức lấy hết dữ liệu từ collection "family"
    public void getAllFamilyData() {
        CollectionReference familyRef = firestore.collection("family");
        familyRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        // Lấy dữ liệu từ mỗi document trong collection "family"
                        Family familyMember = document.toObject(Family.class);
                        if (familyMember != null) {
                            // Xử lý dữ liệu, ví dụ: hiển thị thông tin, thêm vào danh sách, ...
                            familyList.add(familyMember);
                        }
                    }
                } else {
                    // Xử lý lỗi nếu có

                }
            }
        });
    }
}