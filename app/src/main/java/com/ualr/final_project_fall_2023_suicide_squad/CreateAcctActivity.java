package com.ualr.final_project_fall_2023_suicide_squad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAcctActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextEmail;
    private Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_acct_activity);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String email = editTextEmail.getText().toString();

                if (isValidRegistration(username, password, email)) {
                    // Perform registration logic, e.g., save to database
                    Toast.makeText(CreateAcctActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAcctActivity.this, "Invalid registration information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidRegistration(String username, String password, String email) {
        // Add your own logic for validating registration information
        // For now, it's just a placeholder
        return !username.isEmpty() && !password.isEmpty() && !email.isEmpty();
    }
}

