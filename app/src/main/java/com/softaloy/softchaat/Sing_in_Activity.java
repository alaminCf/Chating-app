package com.softaloy.softchaat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.softaloy.softchaat.databinding.ActivitySingInBinding;

public class Sing_in_Activity extends AppCompatActivity {

    ActivitySingInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        progressDialog = new ProgressDialog(Sing_in_Activity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to Your Account");





        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.edtEmail.getText().toString().isEmpty()){
                    binding.edtEmail.setError("Enter your email");

                    return;
                }
                if (binding.edtPassword.getText().toString().isEmpty()){
                    binding.edtPassword.setError("Enter your password");
                    return;
                }

                progressDialog.show();
                auth.signInWithEmailAndPassword(binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressDialog.dismiss();

                                if (task.isSuccessful()){
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }else {
                                    Toast.makeText(Sing_in_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
        binding.createAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sing_in_Activity.this, Sing_up_Activity.class));
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null){
            startActivity(new Intent(Sing_in_Activity.this, MainActivity.class));
            finish();
        }

    }
}