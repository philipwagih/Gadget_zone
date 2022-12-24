package com.example.storeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    //Micky creating objects of DatabaseReference class to access firebase realtime
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-database-default-rtdb.firebaseio.com/");
    private DatePickerDialog picker;
    TextView birthDateTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();

        // Get The Icon Beside The Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);
        birthDateTXT = (TextView) findViewById(R.id.BirthDate);

        final EditText fullname=findViewById(R.id.fullname);
        final EditText email=findViewById(R.id.email);
        final EditText phone=findViewById(R.id.phone);
        final EditText password=findViewById(R.id.password);
        final EditText conPassword=findViewById(R.id.conPassword);

        final EditText address =findViewById(R.id.address);
        final EditText bestfriend =findViewById(R.id.bestfriend);

        final Button registerBtn=findViewById(R.id.registerBtb);
        final TextView loginNowBtn=findViewById(R.id.loginNow);
//        birthdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),Calender.class);
//                startActivity(intent);
//            }
//        });

       //birthdate.setText(Calender.Date);
        //Toast.makeText(Register.this, "Data"+date, Toast.LENGTH_SHORT).show();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // GET The DATA From EdiTexts INTO String variables

                final String fullnameTxt=fullname.getText().toString();
                final String emailTxt=email.getText().toString();
                final String phoneTxt=phone.getText().toString();
                final String passwordTxt=password.getText().toString();
                final String conPasswordTxt=conPassword.getText().toString();
                final String birthdateTxt=birthDateTXT.getText().toString();
                final String addressTxt=address.getText().toString();
                final String bestfriendTxt=bestfriend.getText().toString();
                //String birthDate = birthDateTXT.getText().toString();
                // check if the Fuckin User fill all the fields before sending data to Firebase

                if(fullnameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty()||bestfriendTxt.isEmpty()||addressTxt.isEmpty())
                {
                    Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                // check if passwords are matching
                // if not matching with each other then show a message
                else if(!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(Register.this, "Passwords are not Matching", Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if email verified correct

                            Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                            Matcher mat = pattern.matcher(emailTxt);

                            //check if phone no write correct
                            boolean phoneIsValid = phoneTxt.length() == 11 && ( phoneTxt.startsWith("010") || phoneTxt.startsWith("012") || phoneTxt.startsWith("011") || phoneTxt.startsWith("015"));

                            //check if phone not registered before
                            if(snapshot.hasChild(phoneTxt)){
                                Toast.makeText(Register.this, "Phone is already Registered ", Toast.LENGTH_SHORT).show();
                            }

                            else if ( !phoneIsValid ){
                            Toast.makeText(Register.this, "Please Enter a Valid Phone number ", Toast.LENGTH_SHORT).show();
                            }
                            else if(!mat.matches()){
                            Toast.makeText(Register.this, "Please Enter a Valid Email ", Toast.LENGTH_SHORT).show();
                            }

                            else {

                                //sending data to firebase realtime database.
                                //we are using phone number as unique identify of every user. so all other details come under phone number

                                databaseReference.child("users").child(phoneTxt).child("fullname").setValue(fullnameTxt);
                                databaseReference.child("users").child(phoneTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(phoneTxt).child("password").setValue(passwordTxt);
                                databaseReference.child("users").child(phoneTxt).child("Birth Date").setValue(birthdateTxt);
                                databaseReference.child("users").child(phoneTxt).child("Address").setValue(addressTxt);
                                databaseReference.child("users").child(phoneTxt).child("bestfriend").setValue(bestfriendTxt);

                                // show success message when user registered, then finish the activity.

                                Toast.makeText(Register.this, "User Registered Successfully ", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }



            }

        });
        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        birthDateTXT.setOnClickListener(v -> handleDatePicking());

    }
    private void handleDatePicking(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    monthOfYear++;
                    String date = ""+year+" / "+monthOfYear+" / "+dayOfMonth+"";
                    birthDateTXT.setText(date);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}