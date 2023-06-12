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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class teldgrActivity extends AppCompatActivity {

    Button tlbtn;
    EditText telet;
    DatabaseReference db;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teldgr);

        tlbtn=findViewById(R.id.btntl);
        telet=findViewById(R.id.ettel1);
        mauth=FirebaseAuth.getInstance();
        String userid=mauth.getCurrentUser().getUid();
        db=FirebaseDatabase.getInstance().getReference("kullanicilar").child(userid);
        tlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (telet.getText().toString().length()!=10){
                    Toast.makeText(teldgrActivity.this, "10 haneli telefon no giriniz", Toast.LENGTH_SHORT).show();
                }else {
                    db.child("tel1").setValue(telet.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(teldgrActivity.this,"Telefon no değiştirildi",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(teldgrActivity.this,isciActivity.class));
                                finish();
                            }else {
                                Toast.makeText(teldgrActivity.this,"Başarısız",Toast.LENGTH_SHORT).show();
                                telet.clearComposingText();
                            }

                        }
                    });
                }
            }
        });



    }
}