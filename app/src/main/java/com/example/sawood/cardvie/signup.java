package com.example.sawood.cardvie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity{

    private EditText userName, userPassword, userEmail ,userno;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
   // private ImageView userProfilePic;
    String email, name, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setupUIViews();
        final Login l = new Login();
        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to the database
                    final String user_email = userEmail.getText().toString().trim();
                    final String user_password = userPassword.getText().toString().trim();
                    final ProgressDialog prd = ProgressDialog.show(signup.this,"Registering on Database","please wait..!");

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                //sendEmailVerification();
                                prd.dismiss();
                               // firebaseAuth.signOut();
                                sendUserData();
                                finish();
                                //sendEmailVerification();

                              //  Toast.makeText(signup.this, "Successfully Registered, Upload complete!", Toast.LENGTH_LONG).show();
                                // l.signIn(user_email,user_password);
                               // firebaseAuth.signOut();

                                startActivity(new Intent(signup.this, MainActivity.class));

                            }else{
                                Toast.makeText(signup.this, "Registration Failed:" +task.getException().toString() , Toast.LENGTH_SHORT).show();
                                prd.dismiss();
                            }

                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, Login.class));
            }
        });

    }

    private void setupUIViews(){
        userName = (EditText)findViewById(R.id.sgn_name);
        userPassword = (EditText)findViewById(R.id.sgn_password);
        userEmail = (EditText)findViewById(R.id.sgn_email);
        regButton = (Button)findViewById(R.id.sign_up);
        userLogin = (TextView)findViewById(R.id.go_log);
        userno = (EditText)findViewById(R.id.sgn_phone);
       // userProfilePic = (ImageView)findViewById(R.id.ivProfile);
    }

    private Boolean validate(){
        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        phone = userno.getText().toString();


        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }


    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                       // sendUserData();
                        Toast.makeText(signup.this, "Successfully Registered, Verification mail has been sent to your Email please verify it.", Toast.LENGTH_SHORT).show();
                       //firebaseAuth.signOut();
                       // finish();
                       // startActivity(new Intent(signup.this, Login.class));
                    }else{
                        Toast.makeText(signup.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getInstance().getUid());
        UserProfile userProfile = new UserProfile(phone, email, name);
        myRef.setValue(userProfile);
    }
}
