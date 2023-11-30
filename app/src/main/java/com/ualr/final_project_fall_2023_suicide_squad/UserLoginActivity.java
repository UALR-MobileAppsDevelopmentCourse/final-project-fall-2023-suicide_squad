package com.ualr.final_project_fall_2023_suicide_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import android.content.SharedPreferences;


public class UserLoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_activity);

        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("H4K");

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);


        buttonLogin = findViewById(R.id.buttonLogin);
        Button createAccountButton = findViewById(R.id.btnCreateAccount);

        createAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(UserLoginActivity.this, CreateAcctActivity.class);
                    startActivity(intent);

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String storedUsername = sharedPreferences.getString("username", "");
                String storedPassword = sharedPreferences.getString("password", "");

                String enteredUsername = editTextUsername.getText().toString();
                String enteredPassword = editTextPassword.getText().toString();

                if (isValidLogin(enteredUsername, enteredPassword, storedUsername, storedPassword))
                {
                    Toast.makeText(UserLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    navigateToDashboardOptions();

                } else {
                    Toast.makeText(UserLoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
        private boolean isValidLogin(String enteredUsername, String enteredPassword, String storedUsername, String storedPassword) {
            return enteredUsername.equals(storedUsername) && enteredPassword.equals(storedPassword);

        }


        private void navigateToDashboardOptions() {
            Intent intent = new Intent(UserLoginActivity.this, DashboardOptionsActivity.class);
            startActivity(intent);
            finish();

        }

       }