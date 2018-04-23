package br.com.igbeni.uol.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

@Entity(tableName = "feed_item")
public class FeedItemEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private String type;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "thumb")
    @SerializedName("thumb")
    private String thumb;

    @ColumnInfo(name = "updated")
    @SerializedName("updated")
    private Long updated;

    @ColumnInfo(name = "shareUrl")
    @SerializedName("share-url")
    private String shareUrl;

    @ColumnInfo(name = "webviewUrl")
    @SerializedName("webview-url")
    private String webviewUrl;

    public FeedItemEntity() {
        this.id = UUID.randomUUID().toString();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getWebviewUrl() {
        return webviewUrl;
    }

    public void setWebviewUrl(String webviewUrl) {
        this.webviewUrl = webviewUrl;
    }
}
