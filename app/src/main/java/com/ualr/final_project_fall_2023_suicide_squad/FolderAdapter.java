package com.ualr.final_project_fall_2023_suicide_squad;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private List<Folder> folderList;

    public FolderAdapter(List<Folder> folderList) {
        this.folderList = folderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Folder folder = folderList.get(position);
        holder.textViewFolderName.setText(folder.getName());
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFolderIcon;
        TextView textViewFolderName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewFolderIcon = itemView.findViewById(R.id.imageViewFolderIcon);
            textViewFolderName = itemView.findViewById(R.id.textViewFolderName);
        }
    }
}
