package org.example;
import java.util.ArrayList;

public class KitchenFurnitureSetBuilder implements FurnitureSetBuilder {
    private String setName;
    private  FurnitureSetDB.Materials material;
    private  FurnitureSetDB.Forms form;
    private  FurnitureSetDB.Styles style;
    private ArrayList <String> furniture = new ArrayList<>();

    @Override
    public void setName(String name) {
        this.setName = name + " kitchen";
    }
    @Override
    public void setMaterials(FurnitureSetDB.Materials material) {
        System.out.println("You choose " + material.name() + " material for kitchen");
        this.material = material;
    }

    @Override
    public void setForm(FurnitureSetDB.Forms form) {
        System.out.println("You choose " + form.name() + " form for kitchen");
        this.form = form;
    }

    @Override
    public void setStyle(FurnitureSetDB.Styles style) {
        System.out.println("You choose " + style.name() + " style for kitchen");
        this.style = style;
    }

    @Override
    public void assembleFurniture() {
        System.out.println("Assembling kitchen cabinets");
        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\n Assembling stove");
        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }

    @Override
    public void placeFurniture() {
        System.out.println("Placing kitchen cabinets");
        for (int i = 0; i < 8; i++) {
            System.out.print(".");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        furniture.add("kitchen cabinets");
        System.out.println("\n Placing stove");
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        furniture.add("stove");
        System.out.println("\n Placing other furniture");
        furniture.add("other kitchen furniture");
    }

    @Override
    public void showResult() {
        System.out.println("Now you have " + this.form + " kitchen furniture made from "
                + this.material + " in " + this.style + " style");
    }
    @Override
    public FurnitureSet getResult() {
        return new FurnitureSet(setName, material, form, style, furniture);
    }
}
