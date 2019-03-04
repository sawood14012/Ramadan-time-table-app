package com.example.sawood.cardvie;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {

    private static final String TAG = "AndroidBash";
    //private Firebase myFirebaseRef =new Firebase("https://fitmaker-ee2c0.firebaseio.com/");
    private EditText Name;
    private EditText Password;
    // private TextView Info;
    private Button Login;
    // private int counter = 5;
    private TextView forgotPassword;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private FirebaseAuth.AuthStateListener mAuthState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // ActionBar actionBar = getActionBar();
        //actionBar.hide();


       // Name = (EditText) findViewById(R.id.login_email);
       // Password = (EditText) findViewById(R.id.login_pass);
        Login = (Button) findViewById(R.id.Login_but);
        userRegistration = (TextView) findViewById(R.id.regis);
        forgotPassword = (TextView) findViewById(R.id.Forgot_pass);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser m = firebaseAuth.getCurrentUser();

        if (m!=null){
            Log.d(TAG, "signed in");
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }

        mAuthState = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };




        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate(Name.getText().toString(), Password.getText().toString());
               // progressDialog.setMessage("Please Wait..!!");
              //  progressDialog.show();

                signIn(Name.getText().toString(),Password.getText().toString());
              //  progressDialog.dismiss();


            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Login.this, signup.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, PasswordActivity.class));
            }
        });
    }







    public void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        final ProgressDialog prd = ProgressDialog.show(Login.this,"Logging you in " ,"Please wait");
       // checkEmailVerification();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                       // checkEmailVerification();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            prd.dismiss();
                            Toast.makeText(Login.this, "Authentication failed." +task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                          //  checkEmailVerification();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            String uid = firebaseAuth.getCurrentUser().getUid();
                            intent.putExtra("user_id", uid);
                            prd.dismiss();
                            finish();
                            startActivity(intent);

                        }

                     // progressDialog.dismiss();
                    }
                });
        //
    }



    private boolean validateForm() {
        boolean valid = true;

        String userEmail = Name.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            Name.setError("Required.");
            valid = false;
        } else {
            Name.setError(null);
        }

        String userPassword = Password.getText().toString();
        if (TextUtils.isEmpty(userPassword)) {
            Password.setError("Required.");
            valid = false;
        } else {
            Password.setError(null);
        }

        return valid;
    }



    @Override
    protected void onStart() {
        super.onStart();
        Name = (EditText) findViewById(R.id.login_email);
        Password = (EditText) findViewById(R.id.login_pass);
        firebaseAuth.addAuthStateListener(mAuthState);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthState != null) {
            firebaseAuth.removeAuthStateListener(mAuthState);
        }
    }



    private void checkEmailVerification(){

        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        //startActivity(new Intent(Login.this, MainActivity.class));
        if(emailflag){
            finish();
            startActivity(new Intent(Login.this, MainActivity.class));
        }else{
            Toast.makeText(this, "You have not Verified Your Email Yet..!", Toast.LENGTH_SHORT).show();
            finish();
            firebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
        }
    }

}












