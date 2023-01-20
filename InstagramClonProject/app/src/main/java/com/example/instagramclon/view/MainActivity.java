package com.example.instagramclon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.instagramclon.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setTitle("Giriş Ekranı");

        firebaseAuth=FirebaseAuth.getInstance();

        binding.sifremiUnuttumText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=binding.emaliText.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(MainActivity.this, "Mail bilgisini giriniz", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Sıfırlama Maili gönderildi", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Log.e("Hata",task.getException().toString());
                        }
                    });
                }
            }
        });


    }

    public  void signInClick(View view){

        String email=binding.emaliText.getText().toString();
        String password=binding.passwordText.getText().toString();

        if (email.equals("")||password.equals("")){
            Toast.makeText(MainActivity.this,"Email ve Şifre giriniz",Toast.LENGTH_LONG).show();

        }
        else {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent=new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }


    }


    public void signUpClick(View view){
        Intent intentToSignUp=new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(intentToSignUp);


    }
}