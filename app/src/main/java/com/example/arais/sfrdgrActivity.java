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

public class sfrdgrActivity extends AppCompatActivity {

    FirebaseAuth mauth;
    FirebaseUser user;
    EditText eskisfr,yenisfr,yenisfr2;
    Button dgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfrdgr);

        eskisfr=findViewById(R.id.etsfr1);
        yenisfr=findViewById(R.id.etsfr2);
        yenisfr2=findViewById(R.id.etsfr3);
        dgr=findViewById(R.id.btnsfr);


        mauth=FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(sfrdgrActivity.this,girisActivity.class));
        }
        dgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yenisfr.getText().toString().length()!=6 || !yenisfr.getText().toString().equals(yenisfr2.getText().toString()) ) {
                    Toast.makeText(sfrdgrActivity.this, "Yeni Şifrelerinizi Düzenleyin", Toast.LENGTH_SHORT).show();
                }else {
                    String mail = user.getEmail();
                    AuthCredential credential = EmailAuthProvider.getCredential(mail, eskisfr.getText().toString());
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(yenisfr.getText().toString());
                                Toast.makeText(sfrdgrActivity.this, "Şifre Başarı ile Değiştirildi", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(sfrdgrActivity.this,girisActivity.class));
                            } else {
                                Toast.makeText(sfrdgrActivity.this, "Eski şifreniz yanlış", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}