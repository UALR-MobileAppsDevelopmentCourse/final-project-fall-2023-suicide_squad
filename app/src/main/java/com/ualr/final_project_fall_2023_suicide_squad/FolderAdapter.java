package com.ualr.final_project_fall_2023_suicide_squad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;


public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> implements Filterable {

    private final List<String> folderList;
    private List<String> ListFull;
    private final OnFolderClickListener onFolderClickListener;


    public FolderAdapter(List<String> folderList, OnFolderClickListener onFolderClickListener) {
        this.folderList = folderList;
        this.onFolderClickListener = onFolderClickListener;
        ListFull = new ArrayList<>(folderList);
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

    @Override
    public Filter getFilter() {
        return listFilter;
    }
    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if (constraint == null||constraint.length()==0){
                filteredList.addAll(ListFull);

            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item :ListFull){
                    if (item.toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            folderList.clear();
            if (results.values instanceof List) {
                folderList.addAll((List<String>) results.values);
            }
            notifyDataSetChanged();
        }


    };
}
