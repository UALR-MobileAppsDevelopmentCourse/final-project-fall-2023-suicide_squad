package com.ualr.final_project_fall_2023_suicide_squad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class NewFolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_folder_activity);

        final EditText editTextFolderName = findViewById(R.id.editTextFolderName);
        Button buttonCreateFolder = findViewById(R.id.buttonCreateFolder);

        buttonCreateFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderName = editTextFolderName.getText().toString().trim();
                if (isValidFolderName(folderName)) {
                    // Determine the correct file to use based on the context
                    String fileName = getIntent().getStringExtra("ACTIVITY_CONTEXT").equals("dashboard") ? "folders.txt" : "notes_folders.txt";

                    if (saveFolderName(folderName, fileName)) {
                        Toast.makeText(NewFolderActivity.this, "Folder created successfully", Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("FOLDER_NAME", folderName);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else {
                        Toast.makeText(NewFolderActivity.this, "Error creating folder", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    editTextFolderName.setError("Invalid folder name");
                }
            }
        });

    }

    private boolean saveFolderName(String folderName, String fileName) {
        fileName = getIntent().getStringExtra("ACTIVITY_CONTEXT").equals("dashboard") ? "folders.txt" : "notes_folders.txt";

        try (FileOutputStream fos = openFileOutput(fileName, MODE_APPEND);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            osw.write(folderName + "\n");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidFolderName(String folderName) {
        // Add additional validation logic as needed
        return !folderName.isEmpty();
    }
}
