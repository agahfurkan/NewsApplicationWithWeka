package com.agah.furkan.newsapplicationwithweka.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForBooksRequest;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.RecyclerViewHolder> {
    private AppCompatActivity appCompatActivity;
    private ModelForBooksRequest modelForBooksRequest;

    public BooksListAdapter(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.books_list_itemview, viewGroup, false);
        return new BooksListAdapter.RecyclerViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.booksContent.setText(modelForBooksRequest.getItems().get(recyclerViewHolder.getAdapterPosition()).getVolumeInfo().getDescription());
        recyclerViewHolder.booksTitle.setText(modelForBooksRequest.getItems().get(recyclerViewHolder.getAdapterPosition()).getVolumeInfo().getTitle());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.no_image_icon);
        requestOptions.centerInside();
        if (modelForBooksRequest.getItems().get(recyclerViewHolder.getAdapterPosition()).getVolumeInfo().getImageLinks() != null) {
            Glide.with(recyclerViewHolder.itemView)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(modelForBooksRequest.getItems().get(recyclerViewHolder.getAdapterPosition()).getVolumeInfo().getImageLinks().getThumbnail())
                    .into(recyclerViewHolder.booksPreviewImage);
        }
        recyclerViewHolder.booksCardView.setOnClickListener(v -> {

        });
        recyclerViewHolder.booksOptionsButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.listviewItem_share) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = modelForBooksRequest.getItems().get(recyclerViewHolder.getAdapterPosition()).getVolumeInfo().getInfoLink();
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    appCompatActivity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                } else if (menuItem.getItemId() == R.id.listviewItem_remove) {
                    int pos = recyclerViewHolder.getAdapterPosition();
                    modelForBooksRequest.getItems().remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, modelForBooksRequest.getItems().size());
                }
                return true;
            });
            popup.show();
        });
    }

    public void setList(ModelForBooksRequest modelForBooksRequest) {
        this.modelForBooksRequest = modelForBooksRequest;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (modelForBooksRequest != null) {
            return modelForBooksRequest.getItems().size();
        } else {
            return 0;
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.books_content)
        TextView booksContent;
        @BindView(R.id.books_title)
        TextView booksTitle;
        @BindView(R.id.books_preview_image)
        ImageView booksPreviewImage;
        @BindView(R.id.books_cardView)
        CardView booksCardView;
        @BindView(R.id.books_options_button)
        ImageButton booksOptionsButton;

        private RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
