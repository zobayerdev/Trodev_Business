package com.trodev.trodevbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.trodev.trodevbusiness.password.UploadPassword;
import com.trodev.trodevbusiness.website.UploadWebActivity;

public class AdminScreenActivity extends AppCompatActivity {

    MaterialCardView add_web, add_pass, send_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        /*init views*/
        add_web = findViewById(R.id.add_web);
        add_pass = findViewById(R.id.add_pass);
        send_notification = findViewById(R.id.send_notification);

        add_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenActivity.this, UploadWebActivity.class));
            }
        });

        add_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenActivity.this, UploadPassword.class));
            }
        });

        send_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenActivity.this, SendNotification.class));
            }
        });

    }
}