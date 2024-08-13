package com.trodev.trodevbusiness.website;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.trodev.trodevbusiness.MainActivity;
import com.trodev.trodevbusiness.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadWebActivity extends AppCompatActivity {

    TextInputEditText web_name_et, web_link_et;
    MaterialButton uploadBtn;
    ImageView imageIv;

    /*################################*/
    /*for image link*/ Uri imageUri;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    /*################################*/
    /*firebase data*/
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_web);

        /*init view*/
        imageIv = findViewById(R.id.imageIv);
        web_name_et = findViewById(R.id.web_name_et);
        web_link_et = findViewById(R.id.web_link_et);
        uploadBtn = findViewById(R.id.uploadBtn);

        /*firebase setup*/
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        // #######################################
        /*progressDialog*/
        progressDialog = new ProgressDialog(UploadWebActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog = ProgressDialog.show(this, "আপলোড হচ্ছে", "অপেক্ষা করুন");
        progressDialog.hide();


        // #######################################
        /*set on click listener*/
        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();

            }
        });

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

                final StorageReference reference = storage.getReference().child("trodev_business_link").child(System.currentTimeMillis() + "");
                reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                LinkModel allJobModel = new LinkModel();
                                allJobModel.setImage(uri.toString());

                                allJobModel.setWeb_name(web_name_et.getText().toString().trim());
                                allJobModel.setWeb_link(web_link_et.getText().toString().trim());


                                /*real time date*/
                                allJobModel.setDate(date);
                                allJobModel.setTime(time);

                                database.getReference().child("trodev_business_link").push().setValue(allJobModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.hide();

                                        Intent intent = new Intent(UploadWebActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finishAffinity();

                                        Toast.makeText(UploadWebActivity.this, "আপলোড সম্পূর্ণ হয়েছে !!!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.hide();
                                        Toast.makeText(UploadWebActivity.this, "আপলোড সম্পূর্ণ হয় নাই\nআবার চেষ্টা করুন", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

                    }
                });

            }
        });

    }

    private void uploadImage() {

        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Intent intent = new Intent();
                intent.setType("image/*");
                //  intent.setType("pdf/docs/ppt/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(UploadWebActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageIv.setImageURI(imageUri);
        }
    }
}