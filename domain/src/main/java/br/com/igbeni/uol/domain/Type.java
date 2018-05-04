package br.com.igbeni.uol.domain;

public enum Type {
    NEWS("news"),
    BANNER("banner"),
    DATE("date");

    private String type;

    Type(String type) {
        this.type = type;
    }

    public static Type fromString(String text) {
        for (Type b : Type.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }
}
