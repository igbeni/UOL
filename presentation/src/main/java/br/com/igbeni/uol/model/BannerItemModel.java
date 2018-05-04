package br.com.igbeni.uol.model;

import br.com.igbeni.uol.domain.Type;

public class BannerItemModel extends ItemModel {

    private final String thumb;

    public BannerItemModel(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb() {
        return thumb;
    }

    @Override
    public Type getType() {
        return Type.BANNER;
    }
}
