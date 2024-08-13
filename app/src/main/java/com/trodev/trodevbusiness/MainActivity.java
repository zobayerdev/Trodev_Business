package com.trodev.trodevbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;
import com.trodev.trodevbusiness.password.PasswordActivity;
import com.trodev.trodevbusiness.website.LinkAdapter;
import com.trodev.trodevbusiness.website.LinkModel;
import com.trodev.trodevbusiness.website.UploadWebActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvList;
    private DatabaseReference reference;
    private ArrayList<LinkModel> list;
    private LinkAdapter adapter;
    FloatingActionButton fab, fabOption1, fabOption2;
    private boolean isFabOpen = false;

    // NOTE: Replace the below with your own ONESIGNAL_APP_ID
    private static final String ONESIGNAL_APP_ID = "dfd78af9-59a1-48e2-a4b1-6b96d6db4466";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*onesignal push notification*/
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);
        OneSignal.getNotifications().requestPermission(false, Continue.none());

        /*init view*/
        rvList = findViewById(R.id.rvList);
        fab = findViewById(R.id.fab);
        fabOption1 = findViewById(R.id.fabOption1);
        fabOption2 = findViewById(R.id.fabOption2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFabOpen) {
                    closeFabMenu();
                } else {
                    openFabMenu();
                }
            }
        });

        fabOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
            }
        });

        fabOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
                Toast.makeText(MainActivity.this, "Double tap to body password copy automatic", Toast.LENGTH_LONG).show();
            }
        });

        /*database path*/
        reference = FirebaseDatabase.getInstance().getReference().child("trodev_business_link");

        /*load data*/
        loadQuestions();

    }

    private void openFabMenu() {
        isFabOpen = true;
        fabOption1.setVisibility(View.VISIBLE);
        fabOption2.setVisibility(View.VISIBLE);
        fab.animate().rotation(45f);
        fab.setImageResource(R.drawable.admin_icon);// Rotate the main FAB
    }

    private void closeFabMenu() {
        isFabOpen = false;
        fabOption1.setVisibility(View.GONE);
        fabOption2.setVisibility(View.GONE);
        fab.animate().rotation(0f);
        fab.setImageResource(R.drawable.add_icon);// Rotate back the main FAB
    }

    private void loadQuestions() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LinkModel data = snapshot.getValue(LinkModel.class);
                    list.add(data);
                }


                adapter = new LinkAdapter(MainActivity.this, list);
                rvList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}