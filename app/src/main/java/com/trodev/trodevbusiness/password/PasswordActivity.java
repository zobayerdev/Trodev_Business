package com.trodev.trodevbusiness.password;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trodev.trodevbusiness.R;

import java.util.ArrayList;

public class PasswordActivity extends AppCompatActivity {

    RecyclerView rvList;
    private DatabaseReference reference;
    private ArrayList<PasswordModel> list;
    private PasswordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        /*init view*/
        rvList = findViewById(R.id.rvList);

        /*database path*/
        reference = FirebaseDatabase.getInstance().getReference().child("trodev_web_pass");

        /*load data*/
        loadQuestions();
    }

    private void loadQuestions() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PasswordModel data = snapshot.getValue(PasswordModel.class);
                    list.add(data);
                }

                adapter = new PasswordAdapter(PasswordActivity.this, list);
                rvList.setLayoutManager(new LinearLayoutManager(PasswordActivity.this));
                rvList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PasswordActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}