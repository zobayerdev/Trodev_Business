package com.trodev.trodevbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AdminLoginActivity extends AppCompatActivity {

    private TextInputEditText editTextAdmin, editTextPassword;
    private MaterialButton buttonLogin;

    // Define the correct password
    private static final String CORRECT_PASSWORD = "zobayerdev";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editTextAdmin = findViewById(R.id.editTextAdmin);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        editTextAdmin.setText("admin");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String admin = editTextAdmin.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(admin)) {
                    Toast.makeText(AdminLoginActivity.this, "Please enter admin name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(AdminLoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the password matches
                    if (password.equals(CORRECT_PASSWORD)) {
                        // Password is correct, go to the next activity
                        Intent intent = new Intent(AdminLoginActivity.this, AdminScreenActivity.class);
                        startActivity(intent);
                    } else {
                        // Password is incorrect
                        Toast.makeText(AdminLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}