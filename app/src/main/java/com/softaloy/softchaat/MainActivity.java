package com.softaloy.softchaat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.softaloy.softchaat.Adapter.FragmentAdapter;
import com.softaloy.softchaat.databinding.ActivityMainBinding;
import com.softaloy.softchaat.databinding.ToolbarBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    GoogleSignInClient gsc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tablay.setupWithViewPager(binding.viewpager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), Settings_Activity.class));
                //Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:

                Singout();
                break;

            case R.id.groupChat:

                startActivity(new Intent(getApplicationContext(), Group_Chat_Activity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void Singout() {

        auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(getApplicationContext(), Sing_in_Activity.class));
    }

}