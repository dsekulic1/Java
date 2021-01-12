package ba.unsa.etf.rpr;

public enum SistemBodovanja {
    BINARNO,
    PARCIJALNO,
    PARCIJALNO_SA_NEGATIVNIM;

    public String getValue() {

        // this will refer to the object SMALL
        switch (this) {
            case BINARNO:
                return "BINARNO";

            case PARCIJALNO:
                return "PARCIJALNO";

            case PARCIJALNO_SA_NEGATIVNIM:
                return "PARCIJALNO SA NEGATIVNIM BODOVIMA";

            default:
                return null;
        }
    }

}
