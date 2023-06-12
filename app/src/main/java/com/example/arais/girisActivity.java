package com.example.arais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class girisActivity extends AppCompatActivity {
    Button butonkayit,butongiris;
    private EditText etmail,etsifre;
    private FirebaseAuth mauth;

    public FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        butonkayit=findViewById(R.id.btnkayit);
        butongiris=findViewById(R.id.btngiris);
        etmail=findViewById(R.id.etgirismail);
        etsifre=findViewById(R.id.etgirissifre);
        mauth=FirebaseAuth.getInstance();



        butonkayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(girisActivity.this, kayitActivity.class));
                finish();
            }
        });
        butongiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m1=etmail.getText().toString();
                String s2=etsifre.getText().toString();


                    mauth.signInWithEmailAndPassword(m1,s2).addOnCompleteListener(girisActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(girisActivity.this,isciActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(girisActivity.this, "Giriş Başarısız", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mauth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(girisActivity.this,isciActivity.class));
            finish();
        }
    }



}