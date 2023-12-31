package com.ualr.final_project_fall_2023_suicide_squad;


import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ToggleButton toggleViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_options_activity);

        SearchView searchView = findViewById(R.id.searchView);

        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorLighterElectricGreen));
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        Drawable drawable = searchIcon.getDrawable();
        drawable.setColorFilter(getResources().getColor(R.color.colorLighterElectricGreen), PorterDuff.Mode.SRC_IN);

        toggleViewButton=findViewById(R.id.toggleViewButton);
        toggleViewButton.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked){
                recyclerViewFolders.setLayoutManager(new GridLayoutManager(this, 2));
            }
            else {
                recyclerViewFolders.setLayoutManager(new LinearLayoutManager(this));
            }
        }));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                folderAdapter.getFilter().filter(newText);
                return false;
            }
        });

        recyclerViewFolders = findViewById(R.id.recyclerViewFolders);
        int numberOfColumns = 2; // Adjust the number of columns as needed
        recyclerViewFolders.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        folderList = readSavedFolderNames(); // Initialize folderList with saved folder names
        folderAdapter = new FolderAdapter(folderList, this);
        recyclerViewFolders.setAdapter(folderAdapter);

        Button btnNewFolder = findViewById(R.id.btn_new_folder);
        btnNewFolder.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewFolderActivity.class);
            intent.putExtra("ACTIVITY_CONTEXT", "dashboard");
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
        getSupportActionBar().setTitle("H4K | Dashboard");
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
    }

    @Override
    public void onFolderClick(String folderName) {
        Intent intent = new Intent(this, NotesInFolderActivity.class);
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


    private void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file); // Recursively delete subfolders and files
                }
            }
        }
        folder.delete(); // Delete the folder itself after its contents are deleted
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

