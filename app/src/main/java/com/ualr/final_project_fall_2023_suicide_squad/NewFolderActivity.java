package com.ualr.final_project_fall_2023_suicide_squad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                    saveFolderName(folderName);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("FOLDER_NAME", folderName);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    editTextFolderName.setError("Invalid folder name");
                }
            }
        });
    }

    private void saveFolderName(String folderName) {
        try (FileOutputStream fos = openFileOutput("folders.txt", MODE_APPEND);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            osw.write(folderName + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidFolderName(String folderName) {
        return !folderName.isEmpty();
    }
}
