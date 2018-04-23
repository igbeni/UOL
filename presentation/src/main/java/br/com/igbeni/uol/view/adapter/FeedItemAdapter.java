package br.com.igbeni.uol.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.model.FeedModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedItemAdapter extends RecyclerView.Adapter<FeedItemAdapter.FeedItemViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<FeedItemModel> feedItemModelCollection;
    private OnItemClickListener onItemClickListener;

    @Inject
    FeedItemAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.feedItemModelCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.feedItemModelCollection != null) ? this.feedItemModelCollection.size() : 0;
    }

    @NonNull
    @Override
    public FeedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_feed_item, parent, false);
        return new FeedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedItemViewHolder holder, final int position) {
        final FeedItemModel feedItemModel = this.feedItemModelCollection.get(position);
        holder.textViewTitle.setText(feedItemModel.getTitle());

        Date date = new Date(feedItemModel.getUpdated());
        holder.textViewUpdated.setText(date.toString());

        if (feedItemModel.getThumb() != null) {
            Uri uri = Uri.parse(feedItemModel.getThumb());
            if (uri != null) {
                holder.draweeViewThumb.setImageURI(uri);
                holder.draweeViewThumb.setVisibility(View.VISIBLE);
            } else {
                holder.draweeViewThumb.setVisibility(View.GONE);
            }
        } else {
            holder.draweeViewThumb.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (FeedItemAdapter.this.onItemClickListener != null) {
                FeedItemAdapter.this.onItemClickListener.onFeedItemClicked(feedItemModel);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setFeedModel(FeedModel feedModel) {
        List<FeedItemModel> feedItemModels = feedModel.getFeedItemModels();
        this.validateFeedItemModelCollection(feedItemModels);
        this.feedItemModelCollection = feedItemModels;
        this.notifyDataSetChanged();
    }

    private void validateFeedItemModelCollection(Collection<FeedItemModel> feedItemModelCollection) {
        if (feedItemModelCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setFeedItems(List<FeedItemModel> feedItemModels) {
        this.feedItemModelCollection = feedItemModels;
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onFeedItemClicked(FeedItemModel feedItemModel);
    }

    static class FeedItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView textViewTitle;

        @BindView(R.id.updated)
        TextView textViewUpdated;

        @BindView(R.id.thumb)
        SimpleDraweeView draweeViewThumb;

        FeedItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
