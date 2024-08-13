package com.trodev.trodevbusiness.password;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.trodev.trodevbusiness.AdminScreenActivity;
import com.trodev.trodevbusiness.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadPassword extends AppCompatActivity {

    TextInputEditText web_et, pass_et, username_et;
    MaterialButton uploadBtn;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    /*################################*/
    /*firebase data*/
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_password);

        /*init view*/

        web_et = findViewById(R.id.web_et);
        pass_et = findViewById(R.id.pass_et);
        username_et = findViewById(R.id.username_et);
        uploadBtn = findViewById(R.id.uploadBtn);

        /*firebase setup*/
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        // #######################################
        /*progressDialog*/
        progressDialog = new ProgressDialog(UploadPassword.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog = ProgressDialog.show(this, "আপলোড হচ্ছে", "অপেক্ষা করুন");
        progressDialog.hide();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadBtn.setVisibility(View.GONE);
                progressDialog.show();

                // Create a model instance to hold the data
                PasswordModel allJobModel = new PasswordModel();

                // Set the values from the EditTexts
                allJobModel.setWebsite(web_et.getText().toString().trim());
                allJobModel.setPassword(pass_et.getText().toString().trim());
                allJobModel.setUsername(username_et.getText().toString().trim());

                /* Set the current date and time */
                allJobModel.setDate(date);
                allJobModel.setTime(time);

                // Push the data to Firebase Realtime Database
                database.getReference().child("trodev_web_pass").push().setValue(allJobModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.hide();

                                // Navigate to the AdminScreenActivity
                                Intent intent = new Intent(UploadPassword.this, AdminScreenActivity.class);
                                startActivity(intent);
                                finishAffinity();

                                Toast.makeText(UploadPassword.this, "আপলোড সম্পূর্ণ হয়েছে !!!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.hide();
                                Toast.makeText(UploadPassword.this, "আপলোড সম্পূর্ণ হয় নাই\nআবার চেষ্টা করুন", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

}