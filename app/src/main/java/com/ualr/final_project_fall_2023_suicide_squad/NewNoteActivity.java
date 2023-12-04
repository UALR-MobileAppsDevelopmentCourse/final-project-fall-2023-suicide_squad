package com.ualr.final_project_fall_2023_suicide_squad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class NewNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private String folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note_activity);

        editTextNote = findViewById(R.id.editTextNote);
        Button buttonSaveNote = findViewById(R.id.buttonSaveNote);

        folderName = getIntent().getStringExtra("FOLDER_NAME"); // Get the folder name from the intent

        loadNote(); // Load the note when the activity starts

        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteContent = editTextNote.getText().toString();
                if (!noteContent.isEmpty()) {
                    saveNote(noteContent);
                } else {
                    Toast.makeText(NewNoteActivity.this, "Note is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveNote(String noteContent) {
        try {
            File folder = new File(getFilesDir(), folderName); // Get or create the directory for the folder
            if (!folder.exists()) {
                folder.mkdirs(); // Create the folder directory if it doesn't exist
            }

            File noteFile = new File(folder, "note.txt"); // The file for the note in the folder
            try (FileOutputStream fos = new FileOutputStream(noteFile)) {
                fos.write(noteContent.getBytes()); // Write the note content to the file
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNote() {
        File folder = new File(getFilesDir(), folderName);
        File noteFile = new File(folder, "note.txt");
        if (noteFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(noteFile))) {
                StringBuilder noteContent = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    noteContent.append(line).append('\n');
                }
                editTextNote.setText(noteContent.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
