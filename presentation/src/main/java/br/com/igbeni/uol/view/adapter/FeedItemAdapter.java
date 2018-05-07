/*
 * (C) Copyright 2018.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 *      Iggor Alves
 */

package br.com.igbeni.uol.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.domain.Type;
import br.com.igbeni.uol.model.BannerItemModel;
import br.com.igbeni.uol.model.DateItemModel;
import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.model.FeedModel;
import br.com.igbeni.uol.model.ItemModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FeedItemDecoration.SectionCallback {
    private static final int TYPE_FEED_ITEM = 1;
    private static final int TYPE_BANNER = 2;
    private static final int TYPE_DATE = 3;

    @Nullable
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

    private String previousHeader = "";

    @Override
    public int getItemViewType(int position) {
        ItemModel itemModel = itemModelCollection.get(position);
        switch (itemModel.getType()) {
            case NEWS:
                return TYPE_FEED_ITEM;
            case BANNER:
                return TYPE_BANNER;
            case DATE:
                return TYPE_DATE;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FEED_ITEM: {
                final View view = Objects.requireNonNull(this.layoutInflater).inflate(R.layout.row_feed_item, parent, false);
                return new FeedItemViewHolder(view);
            }
            case TYPE_BANNER: {
                final View view = Objects.requireNonNull(this.layoutInflater).inflate(R.layout.row_banner_item, parent, false);
                return new BannerItemViewHolder(view);
            }
            case TYPE_DATE: {
                final View view = Objects.requireNonNull(this.layoutInflater).inflate(R.layout.row_date_item, parent, false);
                return new DateItemViewHolder(view);
            }
            default:
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
//            case TYPE_DATE:
//                initLayoutDate((DateItemViewHolder) holder, position);
//                break;
            default:
                break;
        }
    }

    private void initLayoutBanner(@NonNull BannerItemViewHolder holder, int position) {
        final BannerItemModel bannerItemModel = (BannerItemModel) this.itemModelCollection.get(position);

        Uri uri = Uri.parse(bannerItemModel.getThumb());
        if (uri != null) {
            Objects.requireNonNull(holder.draweeViewThumb).setImageURI(uri);
            holder.draweeViewThumb.setVisibility(View.VISIBLE);
        } else {
            Objects.requireNonNull(holder.draweeViewThumb).setVisibility(View.GONE);
        }
    }

    private void initLayoutFeedItem(FeedItemViewHolder holder, int position) {
        final FeedItemModel feedItemModel = (FeedItemModel) this.itemModelCollection.get(position);
        Objects.requireNonNull(holder.textViewTitle).setText(feedItemModel.getTitle());
        Objects.requireNonNull(holder.textViewUpdated).setText(formatDate(feedItemModel.getUpdated()));

        if (feedItemModel.getThumb() != null) {
            Uri uri = Uri.parse(feedItemModel.getThumb());
            if (uri != null) {
                Objects.requireNonNull(holder.draweeViewThumb).setImageURI(uri);
                holder.draweeViewThumb.setVisibility(View.VISIBLE);
            } else {
                Objects.requireNonNull(holder.draweeViewThumb).setVisibility(View.GONE);
            }
        } else {
            Objects.requireNonNull(holder.draweeViewThumb).setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (FeedItemAdapter.this.onItemClickListener != null) {
                FeedItemAdapter.this.onItemClickListener.onFeedItemClicked(feedItemModel);
            }
        });
    }

    private String formatDate(Long date) {
        String timeAsString = Long.toString(date);

        String hours = timeAsString.substring(8, 10);
        String minutes = timeAsString.substring(10, 12);

        return hours + 'h' + minutes;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setFeedModel(@NonNull FeedModel feedModel) {
        List<FeedItemModel> feedItemModels = feedModel.getFeedItemModels();
        this.validateFeedItemModelCollection(feedItemModels);
        this.itemModelCollection = feedItemModels;
        this.notifyDataSetChanged();
    }

    private void validateFeedItemModelCollection(@Nullable Collection<FeedItemModel> feedItemModelCollection) {
        if (feedItemModelCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setItems(List<ItemModel> itemModels) {
        this.itemModelCollection = itemModels;
        this.notifyDataSetChanged();
    }

    private void initLayoutDate(DateItemViewHolder holder, int position) {
        final DateItemModel dateItemModel = (DateItemModel) this.itemModelCollection.get(position);

//        holder.textViewDate.setText(formatDate(dateItemModel.getDate()));
    }

    @Override
    public boolean isSection(int position) {
        ItemModel itemModel = itemModelCollection.get(position);
        return itemModel.getType() == Type.DATE;
    }

    @Override
    public CharSequence getSectionHeader(int position) {
        ItemModel itemModel = itemModelCollection.get(position);
        if (itemModel.getType() == Type.DATE) {
            final DateItemModel dateItemModel = (DateItemModel) itemModelCollection.get(position);

            previousHeader = dateItemModel.getDate();

            return dateItemModel.getDate();
        }

        return previousHeader;
    }

    public interface OnItemClickListener {
        void onFeedItemClicked(FeedItemModel feedItemModel);
    }

    static class FeedItemViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.title)
        TextView textViewTitle;

        @Nullable
        @BindView(R.id.updated)
        TextView textViewUpdated;

        @Nullable
        @BindView(R.id.thumb)
        SimpleDraweeView draweeViewThumb;

        FeedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class BannerItemViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.thumb)
        SimpleDraweeView draweeViewThumb;

        BannerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class DateItemViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.date)
        TextView textViewDate;

        DateItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Objects.requireNonNull(textViewDate).setVisibility(View.GONE);
        }
    }
}
