package com.ualr.final_project_fall_2023_suicide_squad;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateAcctActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextEmail;
    private Button buttonCreateAccount;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_acct_activity);

        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");

        //Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                  SharedPreferences.Editor editor = sharedPreferences.edit();
                  editor.putString("username", username);
                  editor.putString("password", password);
                  editor.apply();

                    // Perform registration logic, e.g., save to database
                    Toast.makeText(CreateAcctActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    navigateToLoginPage();

                } else {
                    Toast.makeText(CreateAcctActivity.this, "Invalid registration information", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isValidRegistration(String username, String password, String email) {

        return !username.isEmpty() && !password.isEmpty() && !email.isEmpty();
    }

    private void navigateToLoginPage() {
        Intent intent = new Intent(CreateAcctActivity.this, UserLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

