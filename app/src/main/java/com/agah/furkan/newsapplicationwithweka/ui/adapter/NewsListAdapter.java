package com.agah.furkan.newsapplicationwithweka.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForNewsRequest;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.NewsDetailFragment;
import com.agah.furkan.newsapplicationwithweka.util.DialogFactory;
import com.agah.furkan.newsapplicationwithweka.util.FragmentHelperUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.RecyclerViewHolder> {
    private AppCompatActivity appCompatActivity;
    private ModelForNewsRequest modelForNewsRequest;
    private RecyclerView recyclerView;

    public NewsListAdapter(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = null;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_list_itemview, viewGroup, false);
        return new NewsListAdapter.RecyclerViewHolder(view);
    }

    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.newsContent.setText(modelForNewsRequest.getArticles().get(recyclerViewHolder.getAdapterPosition()).getDescription());
        recyclerViewHolder.newsTitle.setText(modelForNewsRequest.getArticles().get(recyclerViewHolder.getAdapterPosition()).getTitle());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.no_image_icon);
        requestOptions.centerCrop();
        Glide.with(recyclerViewHolder.itemView)
                .applyDefaultRequestOptions(requestOptions)
                .load(modelForNewsRequest.getArticles().get(recyclerViewHolder.getAdapterPosition()).getUrlToImage())
                .into(recyclerViewHolder.newsPreviewImage);
        recyclerViewHolder.cardView.setOnClickListener(v -> FragmentHelperUtil.loadWithAnimAddBackStack(appCompatActivity, NewsDetailFragment.newInstance(modelForNewsRequest.getArticles().get(recyclerViewHolder.getAdapterPosition()))));
        recyclerViewHolder.cardView.setOnLongClickListener(v -> {
            DialogFactory.focusNewsDetailDialog(modelForNewsRequest.getArticles().get(recyclerViewHolder.getAdapterPosition()), appCompatActivity.getSupportFragmentManager());
            recyclerView.setLayoutFrozen(true);
            return true;
        });
        recyclerViewHolder.cardView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                DialogFactory.dismissFocusNewsDetailDialog(appCompatActivity.getSupportFragmentManager());
                recyclerView.setLayoutFrozen(false);
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                DialogFactory.dismissFocusNewsDetailDialog(appCompatActivity.getSupportFragmentManager());
                if(recyclerView!=null){
                    recyclerView.setLayoutFrozen(false);
                }
            }
            return false;
        });
        recyclerViewHolder.imageButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.listviewItem_share) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = modelForNewsRequest.getArticles().get(recyclerViewHolder.getAdapterPosition()).getUrl();
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    appCompatActivity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                } else if (menuItem.getItemId() == R.id.listviewItem_remove) {
                    int pos = recyclerViewHolder.getAdapterPosition();
                    modelForNewsRequest.getArticles().remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, modelForNewsRequest.getArticles().size());
                }
                return true;
            });
            popup.show();
        });
    }

    public void setList(ModelForNewsRequest modelForNewsRequest) {
        this.modelForNewsRequest = modelForNewsRequest;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (modelForNewsRequest != null) {
            return modelForNewsRequest.getArticles().size();
        } else {
            return 0;
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_content)
        TextView newsContent;
        @BindView(R.id.news_title)
        TextView newsTitle;
        @BindView(R.id.news_preview_image)
        ImageView newsPreviewImage;
        @BindView(R.id.news_cardView)
        CardView cardView;
        @BindView(R.id.news_options_button)
        ImageButton imageButton;

        private RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
