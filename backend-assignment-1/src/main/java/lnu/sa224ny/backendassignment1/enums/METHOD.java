package lnu.sa224ny.backendassignment1.enums;

public enum METHOD {
    ItemBased("ItemBased"),
    UserBased("UserBased");

    public final String label;

    METHOD(String label) {
        this.label = label;
    }

}
