package com.ualr.final_project_fall_2023_suicide_squad;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;


public class DashboardOptionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddFolder;
    private SwitchMaterial btnSwitchView;

    private boolean isGridView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_options_activity);

        recyclerView = findViewById(R.id.recyclerView);
        fabAddFolder = findViewById(R.id.fabAddFolder);
        btnSwitchView = findViewById(R.id.btnSwitchView);

        // here we will set up RecyclerView with a default layout manager (that being list view)
        setListLayoutManager();

        // here we are setting up the click listener for the Switch button
        btnSwitchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setGridLayoutManager();
                } else {
                    setListLayoutManager();
                }
            }
        });

        // here we are setting up the click listener for the FAB
        fabAddFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the NewFolderActivity for adding a new folder
                startActivity(new Intent(DashboardOptionsActivity.this, NewFolderActivity.class));
            }
        });
    }

    List<Folder> folderList = getFolderList();
    FolderAdapter folderAdapter = new FolderAdapter(folderList);
        recyclerView.setAdapter(folderAdapter);


    private void setListLayoutManager() {
        isGridView = false;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // may need to update the adapter here
    }

    private void setGridLayoutManager() {
        isGridView = true;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // may need to update the adapter here
    }

    private void navigateToNewFolderActivity() {
        Intent intent = new Intent(DashboardOptionsActivity.this, NewFolderActivity.class);
        startActivity(intent);
        finish();

    }
}
