package br.com.igbeni.uol.data.entity;

import com.google.gson.annotations.SerializedName;

import br.com.igbeni.uol.domain.FeedItem;

public class FeedItemEntity {

    private final int id;

    @SerializedName("type")
    private String type;

    @SerializedName("title")
    private String title;

    @SerializedName("thumb")
    private String thumb;

    @SerializedName("updated")
    private Long updated;

    @SerializedName("share-url")
    private String shareUrl;

    @SerializedName("webview-url")
    private String webviewUrl;

    public FeedItemEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
