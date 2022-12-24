package com.example.storeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-database-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        final EditText phone=findViewById(R.id.res_phone);
        final EditText bst=findViewById(R.id.bestie);
        final Button ret_pass=findViewById(R.id.ret_pass);
        ret_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneTxt =phone.getText().toString();
                final String bestie=bst.getText().toString();

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(phoneTxt))
                        {

                            // mobile is exist in firebase
                            // now check if password entered match with the value in the firebase data

                            final  String bestval = snapshot.child(phoneTxt).child("bestfriend").getValue(String.class);
                            final  String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);

                            if(bestval.equals(bestie)){

                                Toast.makeText(ForgetPassword.this, "your Password is:"+getPassword, Toast.LENGTH_SHORT).show();

                                }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Data not Found",Toast.LENGTH_LONG).show();

                            }

                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}