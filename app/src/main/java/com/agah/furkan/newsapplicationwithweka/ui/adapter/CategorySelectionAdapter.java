package com.agah.furkan.newsapplicationwithweka.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.ui.Interfaces.ICategorySelection;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CategorySelectionAdapter extends RecyclerView.Adapter<CategorySelectionAdapter.RecyclerViewHolder> {
    private List<String> categoriesToSelect;
    private ICategorySelection categorySelection;
    private boolean removeCatogory;

    public CategorySelectionAdapter(ICategorySelection categorySelection) {
        categoriesToSelect = new ArrayList<>();
        this.categorySelection = categorySelection;
        removeCatogory = true;
    }

    public void setRemoveFlag(boolean flag) {
        removeCatogory = flag;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_selection_itemview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, int i) {
        if (removeCatogory) {
            recyclerViewHolder.categoryAddButton.setImageResource(R.drawable.ic_remove_circle_white_24dp);
        } else {
            recyclerViewHolder.categoryAddButton.setImageResource(R.drawable.ic_add_circle_white_24dp);
        }
        recyclerViewHolder.categoryText.setText(categoriesToSelect.get(i));
        recyclerViewHolder.linearLayout.setOnClickListener(v -> {
            if (removeCatogory) {
                if (categoriesToSelect.size() > 1) {
                    categorySelection.removeCategory(recyclerViewHolder.categoryText.getText().toString());
                }
            } else {
                categorySelection.addCategory(recyclerViewHolder.categoryText.getText().toString());
            }

        });
    }

    public void setList(List<String> newList) {
        categoriesToSelect = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categoriesToSelect.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_text)
        TextView categoryText;
        @BindView(R.id.categorySelect_Box)
        LinearLayout linearLayout;
        @BindView(R.id.category_add_button)
        ImageButton categoryAddButton;

        private RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
