package com.example.arais;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link profilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth mauth;
    DatabaseReference db;
    private String userid,ad,soyad,mail,telefon;
    private TextView txtad,txtmail,txttel;
    private TableRow tblc,tbls,tblt,tblm;


    public profilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profilFragment newInstance(String param1, String param2) {
        profilFragment fragment = new profilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profil,container,false);
        mauth=FirebaseAuth.getInstance();
        userid=mauth.getCurrentUser().getUid();
        db=FirebaseDatabase.getInstance().getReference("kullanicilar").child(userid);

        txtad=v.findViewById(R.id.usertxt);
        txtmail=v.findViewById(R.id.ml2);
        txttel=v.findViewById(R.id.tl2);
        txtmail.setText(mauth.getCurrentUser().getEmail());
        tblc=v.findViewById(R.id.tblc);
        tbls=v.findViewById(R.id.tbls);
        tblt=v.findViewById(R.id.tblt);
        tblm=v.findViewById(R.id.tblm);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ad= snapshot.child("ad").getValue().toString()+" "+snapshot.child("soyad").getValue().toString();
                telefon=snapshot.child("tel1").getValue().toString();
                txtad.setText(ad);
                txttel.setText(telefon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getView().getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        tblc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.signOut();
                startActivity(new Intent(getActivity(),girisActivity.class));
            }
        });
        tbls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), sfrdgrActivity.class));

            }
        });
        tblt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), teldgrActivity.class));
            }
        });
        tblm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), maildgrActivity.class));
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}