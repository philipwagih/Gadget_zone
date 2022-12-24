package com.example.storeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-database-default-rtdb.firebaseio.com/");
    public static final String EXTRA_PHONENUMBER = "com.example.storeapp.Login.EXTRA_PHONENUMBER";

    private CheckBox chkBoxRememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get The Icon Beside The Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);

        final EditText phone =findViewById(R.id.phone);
        final EditText password =findViewById(R.id.password);
        final CheckBox remember=findViewById(R.id.remember);
        final Button loginBrn =findViewById(R.id.loginBtn);
        final TextView registerNowBtn =findViewById(R.id.registerNowBtb);
        final TextView forget =findViewById(R.id.forget);

//        SharedPreferences preferences = getSharedPreferences( "checkbox",MODE_PRIVATE) ;
//        String checkbox = preferences.getString("remember","");
//
//        if (checkbox.equals ("true")) {
//            Intent i = new Intent(Login.this,MainActivity.class);
//            startActivity(i);
//            finish();
//        }
//        else if (checkbox.equals ("false")){
//            Toast.makeText(Login.this, "sign in", Toast.LENGTH_SHORT).show();
//        }

        loginBrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String phoneTxt =phone.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(phoneTxt.isEmpty() || passwordTxt.isEmpty()){

                    Toast.makeText(Login.this, "Please enter your Mobile No. and Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //CHECK IF PHONE NO. IS EXIST ON FIREBASE DATABASE
                            if(snapshot.hasChild(phoneTxt))
                            {

                                // mobile is exist in firebase
                                // now check if password entered match with the value in the firebase data

                                final  String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);
                                if(getPassword.equals(passwordTxt)){

                                    if(getPassword.equals("admin")){
                                        Intent intent = new Intent(Login.this,Admin_panel.class);
                                        intent.putExtra(EXTRA_PHONENUMBER,phoneTxt);
                                        startActivity(intent);
                                        Toast.makeText(Login.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                        //finish();
                                    }
                                    else {// open MainActivity when Successfull login
                                        Intent i = new Intent(Login.this, MainActivity.class);
                                        i.putExtra(EXTRA_PHONENUMBER, phoneTxt);
                                        startActivity(i);
                                        //finish();
                                        Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();

                                    }

                                }

                                else {
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(Login.this, "Wrong Mobile Number", Toast.LENGTH_SHORT).show();

                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });
//        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (compoundButton.isChecked ()) {
//                    SharedPreferences preferences = getSharedPreferences( "checkbox",MODE_PRIVATE) ;
//                    SharedPreferences.Editor editor = preferences .edit() ;
//                    editor .putString( "remember","true");
//                    editor. apply ( ) ;
//                    Toast.makeText( Login.this,"Checked", Toast. LENGTH_SHORT).show() ;
//                }
//                else if (!compoundButton.isChecked ()) {
//                    SharedPreferences preferences = getSharedPreferences( "checkbox",MODE_PRIVATE) ;
//                    SharedPreferences.Editor editor = preferences.edit() ;
//                    editor .putString( "remember","false");
//                    editor. apply ( ) ;
//                    Toast.makeText( Login.this,"unChecked", Toast. LENGTH_SHORT).show() ;
//                }
//            }
//        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // open register activity
                startActivity(new Intent(Login.this,Register.class));
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgetPassword.class));

            }
        });




    }

}
