package com.softaloy.softchaat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.softaloy.softchaat.Model.Users;
import com.softaloy.softchaat.databinding.ActivitySingUpBinding;

public class Sing_up_Activity extends AppCompatActivity {

    ActivitySingUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

      auth = FirebaseAuth.getInstance();
      database = FirebaseDatabase.getInstance();


      progressDialog = new ProgressDialog(Sing_up_Activity.this);
      progressDialog.setTitle("Creating Account");
      progressDialog.setMessage("We'r Creating Your Account");


      binding.btnRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              progressDialog.show();
              auth.createUserWithEmailAndPassword
                      (binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString())
                      .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {

                      progressDialog.dismiss();
                      if (task.isSuccessful()){

                          Users users = new Users(binding.edtUsername.getText().toString(), binding.edtEmail.getText().toString(),
                                  binding.edtPassword.getText().toString());

                          String id = task.getResult().getUser().getUid();
                          database.getReference().child("Users").child(id).setValue(users);

                          Toast.makeText(Sing_up_Activity.this, "User Created Successful", Toast.LENGTH_SHORT).show();
                      }else {
                          Toast.makeText(Sing_up_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      }

                  }
              });

          }
      });

        binding.havenAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sing_up_Activity.this, Sing_in_Activity.class));
            }
        });



    }
}