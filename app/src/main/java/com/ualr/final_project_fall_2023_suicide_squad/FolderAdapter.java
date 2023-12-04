package com.ualr.final_project_fall_2023_suicide_squad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.Button;



public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private final List<String> folderList;
    private final OnFolderClickListener onFolderClickListener;

    public FolderAdapter(List<String> folderList, OnFolderClickListener onFolderClickListener) {
        this.folderList = folderList;
        this.onFolderClickListener = onFolderClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String folderName = folderList.get(position);
        holder.folderButton.setText(folderName);
        holder.folderButton.setOnClickListener(v -> onFolderClickListener.onFolderClick(folderName));
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        Button folderButton;

        ViewHolder(View itemView) {
            super(itemView);
            folderButton = itemView.findViewById(R.id.buttonFolder);
        }
    }



    public interface OnFolderClickListener {
        void onFolderClick(String folderName);
    }
}
