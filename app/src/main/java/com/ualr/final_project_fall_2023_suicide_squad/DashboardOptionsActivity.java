package com.ualr.final_project_fall_2023_suicide_squad;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.search.SearchBar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class DashboardOptionsActivity extends AppCompatActivity implements FolderAdapter.OnFolderClickListener {

    private static final int NEW_FOLDER_REQUEST_CODE = 1;
    private RecyclerView recyclerViewFolders;
    private FolderAdapter folderAdapter;
    private List<String> folderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_options_activity);


        recyclerViewFolders = findViewById(R.id.recyclerViewFolders);
        int numberOfColumns = 2; // Adjust the number of columns as needed
        recyclerViewFolders.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        folderList = readSavedFolderNames(); // Initialize folderList with saved folder names
        folderAdapter = new FolderAdapter(folderList, this);
        recyclerViewFolders.setAdapter(folderAdapter);

        Button btnNewFolder = findViewById(R.id.btn_new_folder);
        btnNewFolder.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardOptionsActivity.this, NewFolderActivity.class);
            startActivityForResult(intent, NEW_FOLDER_REQUEST_CODE);
        });

        // Initialize swipe-to-delete functionality
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // Handle item movement if needed (not used in swipe-to-delete)
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String folderNameToDelete = folderList.get(position);

                // Delete the folder from storage
                File folderToDelete = new File(getFilesDir(), folderNameToDelete);
                if (folderToDelete.exists() && folderToDelete.isDirectory()) {
                    deleteFolder(folderToDelete);
                }

                // Remove the folder from your list and notify the adapter
                folderList.remove(position);
                folderAdapter.notifyItemRemoved(position);

                // Save the updated folder list to storage (optional, if you want to persist the changes)
                saveFolderNames(folderList);
            }
        };

        // Attach swipe-to-delete functionality to the RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewFolders);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard Options");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onFolderClick(String folderName) {
        Intent intent = new Intent(this, CreatedFolderActivity.class);
        intent.putExtra("FOLDER_NAME", folderName);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_FOLDER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String newFolderName = data.getStringExtra("FOLDER_NAME");
            folderList.add(newFolderName);
            folderAdapter.notifyDataSetChanged();
        }
    }

    private List<String> readSavedFolderNames() {
        List<String> folderNames = new ArrayList<>();
        try (FileInputStream fis = openFileInput("folders.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String folderName;
            while ((folderName = br.readLine()) != null) {
                folderNames.add(folderName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return folderNames;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file);
                }
            }
        }
        folder.delete();
    }

    private void saveFolderNames(List<String> folderNames) {
        try (FileOutputStream fos = openFileOutput("folders.txt", Context.MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {

            for (String folderName : folderNames) {
                bw.write(folderName);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}

