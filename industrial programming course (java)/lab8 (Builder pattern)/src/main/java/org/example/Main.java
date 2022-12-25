package org.example;

public class Main {
    public static void main(String[] args) {
        FurnitureSetBuilder kitchen = new KitchenFurnitureSetBuilder();
        AutoBuilder autoBuilder = new AutoBuilder(kitchen);
        FurnitureSet set1 = autoBuilder.buildFurnitureSetDarkKitchen();
        System.out.println("Set1 name " + set1.getSetName());
        FurnitureSetBuilder bedroom = new BedroomFurnitureSetBuilder();
        autoBuilder = new AutoBuilder(bedroom);
        FurnitureSet set2 = autoBuilder.buildFurnitureSetComfyBedroom();
        System.out.println("Set2 name " + set2.getSetName());
    }
}