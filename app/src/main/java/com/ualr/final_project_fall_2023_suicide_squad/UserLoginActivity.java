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
import android.widget.CheckBox;

public class UserLoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private CheckBox checkBoxOptions1, checkBoxOption2, checkBoxOption3;

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
        CheckBox checkBoxOption1 = findViewById(R.id.checkBoxOption1);
        checkBoxOption2 = findViewById(R.id.checkBoxOption2);
        checkBoxOption3 = findViewById(R.id.checkBoxOption3);
        buttonLogin = findViewById(R.id.buttonLogin);
        Button createAccountButton = findViewById(R.id.btnCreateAccount);

        createAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isLoggedIn()) {
                    saveContactData();
                    Toast.makeText(UserLoginActivity.this, "Contact data saved", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(UserLoginActivity.this, CreateAcctActivity.class);
                    startActivity(intent);
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String storedUsername = sharedPreferences.getString("username", "");
                String storedPassword = sharedPreferences.getString("password", "");

                boolean storedOption1 = sharedPreferences.getBoolean("option1", false);
                boolean storedOption2 = sharedPreferences.getBoolean("option2", false);
                boolean storedOption3 = sharedPreferences.getBoolean("option3", false);

                String enteredUsername = editTextUsername.getText().toString();
                String enteredPassword = editTextPassword.getText().toString();

                if (isValidLogin(enteredUsername, enteredPassword, storedUsername, storedPassword) &&
                validateOptions(storedOption1, storedOption2, storedOption3)) {
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

        private boolean validateOptions(boolean storedOption1, boolean storedOption2, boolean storedOption3) {
         return true;
        }

        private boolean isLoggedIn() {

        String enteredUsername = editTextUsername.getText().toString();
        String enteredPassword = editTextPassword.getText().toString();
        return !enteredUsername.isEmpty() && !enteredPassword.isEmpty();
        }
        private void navigateToDashboardOptions() {
            Intent intent = new Intent(UserLoginActivity.this, DashboardOptionsActivity.class);
            startActivity(intent);
            finish();

        }

        private void saveContactData() {
        String contactName = editTextUsername.getText().toString();
        String contactPassword = editTextPassword.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("contact_name", contactName);
        editor.putString("contact_password", contactPassword);

        editor.apply();
        }
}