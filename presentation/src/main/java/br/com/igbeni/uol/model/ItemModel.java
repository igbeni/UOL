package br.com.igbeni.uol.model;

import br.com.igbeni.uol.domain.Type;

public abstract class ItemModel {

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
