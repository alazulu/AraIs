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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class maildgrActivity extends AppCompatActivity {

    FirebaseAuth mauth;
    FirebaseUser user;
    EditText mail,chksfr;
    Button dgsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maildgr);

        dgsr=findViewById(R.id.btnml);
        chksfr=findViewById(R.id.chksfr);
        mail=findViewById(R.id.dgrmail);


        mauth=FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(maildgrActivity.this,girisActivity.class));
        }
        dgsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eskimail=user.getEmail();
                AuthCredential credential = EmailAuthProvider.getCredential(eskimail, chksfr.getText().toString());
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updateEmail(mail.getText().toString());
                                Toast.makeText(maildgrActivity.this, "Mail Başarı ile Değiştirildi", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(maildgrActivity.this,girisActivity.class));
                            } else {
                                Toast.makeText(maildgrActivity.this, "İşlem Başarısız", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

        });
    }
}