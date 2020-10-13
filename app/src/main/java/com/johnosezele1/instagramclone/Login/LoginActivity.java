package com.johnosezele1.instagramclone.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.johnosezele1.instagramclone.Home.HomeActivity;
import com.johnosezele1.instagramclone.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    private TextView mPleaseWait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressBar = findViewById(R.id.progressBar);
        mEmail = findViewById(R.id.input_email_Login);
        mPassword = findViewById(R.id.input_password_Login);
        mPleaseWait = findViewById(R.id.pleaseWait);
        mContext = LoginActivity.this;
        Log.d(TAG, "onCreate: logging In");

        mPleaseWait.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        init();
        setupFirebaseAuth();
    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string is null");
        if(string.equals("")){
            return true;
        } else {
            return false;
        }
    }
      /*
    -------------------------------------Firebase------------------------------------------------------------
     */
      private void init(){

          //initialize button for logging in
          Button btnLogin = findViewById(R.id.btn_login);
          btnLogin.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Log.d(TAG, "onClick: attempting to log in.");
                  String email = mEmail.getText().toString();
                  String password = mPassword.getText().toString();

                  if (isStringNull(email) && isStringNull(password)) {
                      Toast.makeText(mContext, "you must fill out all the fields", Toast.LENGTH_SHORT).show();
                  } else if (isStringNull(email) || isStringNull(password)){
                      Toast.makeText(mContext, "you must fill out all the fields", Toast.LENGTH_SHORT).show();
                  }
                  else {
                      mProgressBar.setVisibility(View.VISIBLE);
                      mPleaseWait.setVisibility(View.VISIBLE);

                      mAuth.signInWithEmailAndPassword(email, password)
                              .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                  @Override
                                  public void onComplete(@NonNull Task<AuthResult> task) {
                                      Log.d(TAG, "signInWithEmail:onComplete: " + task.isSuccessful());

                                      //If sign in fails, display a message to the user. If sign in succeeds
                                      //the auth state listener will be notified and logic to handle
                                      //the signed in user can be handled in the listener.
                                      if (!task.isSuccessful()) {
                                          Log.w(TAG, "signInWithEmail:onComplete:Failed", task.getException());

                                          Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                                                  Toast.LENGTH_SHORT).show();
                                          mProgressBar.setVisibility(View.GONE);
                                          mPleaseWait.setVisibility(View.GONE);
                                      } else {
                                          Log.d(TAG, "signInWithEmail: Successful Login");
                                          Toast.makeText(LoginActivity.this, getString(R.string.auth_successful),
                                                  Toast.LENGTH_SHORT).show();
                                          mProgressBar.setVisibility(View.GONE);
                                          mPleaseWait.setVisibility(View.GONE);
                                          Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  }
                              });
                  }
              }
          });

          TextView linkSignUp = findViewById(R.id.link_signup);
          linkSignUp.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Log.d(TAG, "onClick: navigating to registration screen");
                  Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                  startActivity(intent);
              }
          });
      }
    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged: signed_in" + user.getUid());
                }else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
