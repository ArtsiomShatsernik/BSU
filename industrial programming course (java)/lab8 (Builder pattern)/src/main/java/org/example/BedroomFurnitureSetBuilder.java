package org.example;

import java.util.ArrayList;

public class BedroomFurnitureSetBuilder implements FurnitureSetBuilder {
    private String setName;
    private  FurnitureSetDB.Materials material;
    private  FurnitureSetDB.Forms form;
    private  FurnitureSetDB.Styles style;
    private ArrayList<String> furniture = new ArrayList<>();

    @Override
    public void setName(String name) {
        this.setName = name + " bedroom";
    }

    @Override
    public void setMaterials(FurnitureSetDB.Materials material) {
        System.out.println("You choose " + material.name() + " material for bedroom");
        this.material = material;
    }

    @Override
    public void setForm(FurnitureSetDB.Forms form) {
        System.out.println("You choose " + form.name() + " form for bedroom");
        this.form = form;
    }

    @Override
    public void setStyle(FurnitureSetDB.Styles style) {
        System.out.println("You choose " + style.name() + " style for bedroom");
        this.style = style;
    }

    @Override
    public void assembleFurniture() {
        System.out.println("Assembling bedroom cabinets");
        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\n Assembling dresser");
        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }

    @Override
    public void placeFurniture() {
        System.out.println("Placing bedroom cabinets");
        for (int i = 0; i < 8; i++) {
            System.out.print(".");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        furniture.add("bedroom cabinets");
        System.out.println("\n Placing dresser");
        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        furniture.add("dresser");
        System.out.println("\n Placing other furniture");
        furniture.add("other bedroom furniture");
    }

    @Override
    public void showResult() {
        System.out.println("Now you have " + this.form + " bedroom furniture made from "
                + this.material + " in " + this.style + " style");
    }
    @Override
    public FurnitureSet getResult() {
        return new FurnitureSet(setName, material, form, style, furniture);
    }
}
