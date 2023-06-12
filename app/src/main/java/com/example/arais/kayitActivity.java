package com.example.arais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class kayitActivity extends AppCompatActivity {

    Button kaydet,don;
    EditText isim, soyisim,tel,mail,sifre,resifre;
    Spinner spingender,spinktur;
    ArrayAdapter<CharSequence> adapgen;
    ArrayAdapter<CharSequence> adapktur;
    String kyttur,kytgender;
    private FirebaseAuth mauth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        calistir();

        mauth=FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference();





        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1=sifre.getText().toString();
                String s2=resifre.getText().toString();
                String e1=mail.getText().toString();

                if ((s1.length() != 6) || !s1.equals(s2)) {
                    Toast.makeText(kayitActivity.this, "Şifrelerinizi düzenleyin", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(e1)) {
                    Toast.makeText(kayitActivity.this, "Email boş olamaz", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(isim.getText().toString()) || TextUtils.isEmpty(soyisim.getText().toString())) {
                    Toast.makeText(kayitActivity.this, "Lütfen ad ve soyadınızı giriniz", Toast.LENGTH_SHORT).show();
                } else if (kytgender.equals("Cinsiyetinizi Seçiniz") || kyttur.equals("Katılım Türünüzü Seçiniz")) {
                    Toast.makeText(kayitActivity.this, "Lütfen cinsiyet ve katılım türünüzü seçiniz", Toast.LENGTH_SHORT).show();
                } else if (tel.getText().toString().length()!=10) {
                    Toast.makeText(kayitActivity.this, "Telefon numaranızı doğru giriniz", Toast.LENGTH_SHORT).show();
                } else {
                    mauth.createUserWithEmailAndPassword(e1,s1).addOnCompleteListener(kayitActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(kayitActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                String userid=mauth.getUid();
                                kayitekle(userid,isim.getText().toString().toUpperCase(),soyisim.getText().toString().toUpperCase(),tel.getText().toString(),kytgender,kyttur);
                                startActivity(new Intent(kayitActivity.this, girisActivity.class));
                                finish();
                            }else {
                                Toast.makeText(kayitActivity.this, "Kayıt Başarısız", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
            }
        });

        don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(kayitActivity.this, girisActivity.class));
                finish();

            }
        });
    }

    public void calistir(){
        kaydet= findViewById(R.id.kayitbtnkayit);
        don= findViewById(R.id.kayitbtndon);
        isim= findViewById(R.id.etisim);
        soyisim= findViewById(R.id.etsoyisim);
        tel= findViewById(R.id.ettel);
        mail= findViewById(R.id.etmail);
        sifre= findViewById(R.id.kayitetsifre);
        resifre= findViewById(R.id.resifre);
        spingender=findViewById(R.id.spincinsiyet);
        spinktur=findViewById(R.id.spintur);

        adapgen=ArrayAdapter.createFromResource(this,R.array.stgender, android.R.layout.simple_spinner_item);
        adapgen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spingender.setAdapter(adapgen);
        adapktur=ArrayAdapter.createFromResource(this,R.array.sttur, android.R.layout.simple_spinner_item);
        adapktur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinktur.setAdapter(adapktur);

        spingender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kytgender=spingender.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinktur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kyttur=spinktur.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class User {

        public String ad,soyad,tel1,cinsiyet,tur;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String ad, String soyad,String tel1, String cinsiyet,String tur) {
            this.ad = ad;
            this.soyad = soyad;
            this.tel1=tel1;
            this.cinsiyet=cinsiyet;
            this.tur=tur;
        }

    }

    public void kayitekle(String userID,String ad, String soyad,String tel1, String cinsiyet,String tur){
        User user=new User(ad, soyad, tel1, cinsiyet, tur);
        db.child("kullanicilar").child(userID).setValue(user);
    }

}