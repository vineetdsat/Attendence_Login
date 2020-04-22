package com.example.attendance_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    static final String TAG = "YOUR-TAG-NAME";
    EditText Name,Employee,Email,Phone,DOB;
    Button Update;
    TextView Top;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update);
        Name = findViewById(R.id.update_name);

        Employee = findViewById(R.id.update_emp);
        Email = findViewById(R.id.update_email);
        Phone = findViewById(R.id.update_phone);
        DOB = findViewById(R.id.update_dob);
        Update = findViewById(R.id.bt_update);
        Top = findViewById(R.id.top_navig);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Name.setText(documentSnapshot.getString("Name"));
                Employee.setText(documentSnapshot.getString("Employee Code"));
                Phone.setText(documentSnapshot.getString("Phone"));
                Email.setText(documentSnapshot.getString("Email"));
                DOB.setText(documentSnapshot.getString("Date Of Birth"));
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString().trim();
                final String emp = Employee.getText().toString().trim();
                final String name = Name.getText().toString().trim();
                final String phone = Phone.getText().toString().trim();
                final String dob = DOB.getText().toString().trim();
                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userID);


                Map<String,Object> user = new HashMap<>();
                user.put("Name",name);
                user.put("Employee Code",emp);
                user.put("Email",email);
                user.put("Phone",phone);
                user.put("D.O.B",dob);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"OnSuccess: User Profile is Updated "+ userID);
                        Toast.makeText(Update.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
