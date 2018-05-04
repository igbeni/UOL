package br.com.igbeni.uol.model;

import br.com.igbeni.uol.domain.Type;

public class DateItemModel extends ItemModel {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public Type getType() {
        return Type.DATE;
    }
}
