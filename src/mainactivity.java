package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.jar.JarEntry;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager pagerview;
    private TabLayout tablayout;
    private TabsLinkChanger  adapter;
    private Button logout;

    private FirebaseUser mainuser;
    private FirebaseAuth mAuth;
    private DatabaseReference Rootref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mainuser = mAuth.getCurrentUser();
        Rootref = FirebaseDatabase.getInstance().getReference();

        pagerview = (ViewPager) findViewById(R.id.main_tabs_pager);
        adapter = new TabsLinkChanger(getSupportFragmentManager());
        pagerview.setAdapter(adapter);

        tablayout = (TabLayout) findViewById(R.id.main_tabs);
        tablayout.setupWithViewPager(pagerview);

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                SendUserToLoginActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mainuser == null){
            SendUserToLoginActivity();
        }
    }

    private void SendUserToLoginActivity(){
        Intent loginintent = new Intent(MainActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginintent);
    }

    private void status(String status){
        Rootref = FirebaseDatabase.getInstance().getReference("Users").child(mainuser.getEmail());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",status);

        Rootref.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("Offline");
    }
}
