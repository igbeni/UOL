package br.com.igbeni.uol.domain;

public class FeedItem {

    private final int id;

    private FeedItem.Type type;

    private String title;

    private String thumb;

    private Long updated;

    private String shareUrl;

    private String webviewUrl;

    public FeedItem(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public enum Type {
        NEWS("news");

        private String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static Type fromString(String text) {
            for (Type b : Type.values()) {
                if (b.type.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }
    }
}
