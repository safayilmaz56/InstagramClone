package com.example.instagramclon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.instagramclon.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Kayıt Ol");

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

    }

    public void signUp1(View view){
        String email = binding.editTextEmail.getText().toString();
        String fullName = binding.editTextFullName.getText().toString();
        String username = binding.editTextUsername.getText().toString();
        String password = binding.editTextPassword.getText().toString();


        if (email.matches("") || fullName.matches("") || username.matches("") || password.matches("")) {
            Toast.makeText(SignUpActivity.this,"Boş alan bırakmayınız.",Toast.LENGTH_SHORT).show();
        }
        else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    setFirebaseFirestoreUsers(email,fullName,username);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
    private void setFirebaseFirestoreUsers(String email, String fullName, String username) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("useremail",email);
        userData.put("user_fullname",fullName);
        userData.put("username",username);
        userData.put("user_id",FirebaseAuth.getInstance().getUid());
        userData.put("user_pphoto",null);
        userData.put("user_birth",null);
        userData.put("user_phone",null);
        userData.put("user_bio",null);

        firestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
                        finishAffinity();
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}