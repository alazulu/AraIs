package com.example.arais;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class isciActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isci);
        fab=findViewById(R.id.fab);
        bnv=findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new anaFragment()).commit();
        bnv.setSelectedItemId(R.id.nav_ana);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fr=null;
                int id= item.getItemId();
                Intent intent=getIntent();

                if (id==R.id.nav_ana){
                    fr=new anaFragment();
                } else if (id==R.id.nav_bil) {
                    fr=new bilFragment();
                } else if (id==R.id.nav_profil) {
                    fr=new profilFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fr).commit();
                return true;
            }
        });


    }


}