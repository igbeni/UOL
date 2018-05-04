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
import java.util.List;

import javax.inject.Inject;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.domain.Type;
import br.com.igbeni.uol.model.BannerItemModel;
import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.model.FeedModel;
import br.com.igbeni.uol.model.ItemModel;
import br.com.igbeni.uol.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FEED_ITEM = 1;
    private static final int TYPE_BANNER = 2;

    private final LayoutInflater layoutInflater;
    private List<? extends ItemModel> itemModelCollection;
    private OnItemClickListener onItemClickListener;

    @Inject
    FeedItemAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemModelCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.itemModelCollection != null) ? this.itemModelCollection.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        ItemModel itemModel = itemModelCollection.get(position);
        if (itemModel.getType() == Type.NEWS) {
            return TYPE_FEED_ITEM;
        } else if (itemModel.getType() == Type.BANNER) {
            return TYPE_BANNER;
        } else {
            return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FEED_ITEM) {
            final View view = this.layoutInflater.inflate(R.layout.row_feed_item, parent, false);
            return new FeedItemViewHolder(view);
        } else if (viewType == TYPE_BANNER) {
            final View view = this.layoutInflater.inflate(R.layout.row_banner_item, parent, false);
            return new BannerItemViewHolder(view);
        } else {
            throw new RuntimeException("The type has to be FEED_ITEM or BANNER");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_FEED_ITEM:
                initLayoutFeedItem((FeedItemViewHolder) holder, position);
                break;
            case TYPE_BANNER:
                initLayoutBanner((BannerItemViewHolder) holder, position);
                break;
            default:
                break;
        }
    }

    private void initLayoutBanner(BannerItemViewHolder holder, int position) {
        final BannerItemModel bannerItemModel = (BannerItemModel) this.itemModelCollection.get(position);

        Uri uri = Uri.parse(bannerItemModel.getThumb());
        if (uri != null) {
            holder.draweeViewThumb.setImageURI(uri);
            holder.draweeViewThumb.setVisibility(View.VISIBLE);
        } else {
            holder.draweeViewThumb.setVisibility(View.GONE);
        }
    }

    private void initLayoutFeedItem(FeedItemViewHolder holder, int position) {
        final FeedItemModel feedItemModel = (FeedItemModel) this.itemModelCollection.get(position);
        holder.textViewTitle.setText(feedItemModel.getTitle());
        holder.textViewUpdated.setText(Utils.formatDate(feedItemModel.getUpdated()));

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
        this.itemModelCollection = feedItemModels;
        this.notifyDataSetChanged();
    }

    private void validateFeedItemModelCollection(Collection<FeedItemModel> feedItemModelCollection) {
        if (feedItemModelCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setItems(List<ItemModel> itemModels) {
        this.itemModelCollection = itemModels;
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

    static class BannerItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumb)
        SimpleDraweeView draweeViewThumb;

        BannerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
