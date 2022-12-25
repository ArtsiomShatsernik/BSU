package org.example;

import java.util.ArrayList;

public class FurnitureSet {
    private final String setName;
    private final FurnitureSetDB.Materials material;
    private final FurnitureSetDB.Forms form;
    private final FurnitureSetDB.Styles style;
    private final ArrayList<String> furniture;

    public FurnitureSet(String setName, FurnitureSetDB.Materials material, FurnitureSetDB.Forms form, FurnitureSetDB.Styles style, ArrayList<String> furniture) {
        this.setName = setName;
        this.material = material;
        this.form = form;
        this.style = style;
        this.furniture = furniture;
    }

    public FurnitureSetDB.Materials getMaterial() {
        return material;
    }

    public FurnitureSetDB.Forms getForm() {
        return form;
    }

    public FurnitureSetDB.Styles getStyle() {
        return style;
    }
    public String getSetName() {
        return setName;
    }

    public ArrayList<String> getFurniture() {
        return furniture;
    }

}
