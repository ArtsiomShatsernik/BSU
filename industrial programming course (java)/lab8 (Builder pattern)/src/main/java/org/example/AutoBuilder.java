package org.example;

public class AutoBuilder {
    private FurnitureSetBuilder builder;
    public AutoBuilder(FurnitureSetBuilder builder) {
        this.builder = builder;
    }
    public FurnitureSet buildFurnitureSetDarkKitchen() {
        builder.fileRead("darkKitchen.txt");
        builder.assembleFurniture();
        builder.placeFurniture();
        builder.showResult();
        return builder.getResult();
    }
    public FurnitureSet buildFurnitureSetComfyBedroom() {
        builder.fileRead("comfyBedroom.txt");
        builder.assembleFurniture();
        builder.placeFurniture();
        builder.showResult();
        return builder.getResult();
    }

}
