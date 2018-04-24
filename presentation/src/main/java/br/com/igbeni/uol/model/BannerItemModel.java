package br.com.igbeni.uol.model;

public class BannerItemModel extends ItemModel {

    private final String thumb;

    public BannerItemModel(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb() {
        return thumb;
    }

}
