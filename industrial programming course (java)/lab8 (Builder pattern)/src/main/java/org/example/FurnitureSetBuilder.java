package org.example;

import java.io.IOException;
import java.util.ArrayList;

public interface FurnitureSetBuilder {
    void setName(String name);
    void setMaterials(FurnitureSetDB.Materials material);
    void setForm(FurnitureSetDB.Forms form);
    void setStyle(FurnitureSetDB.Styles style);
    void assembleFurniture();
    void placeFurniture();
    void showResult();
    FurnitureSet getResult();
    default void fileRead(String fileName) {
            SingletonReader reader = SingletonReader.getInstance();
            ArrayList<String> data;
            try {
                data = reader.readDB(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setName(data.get(0));
            setForm(FurnitureSetDB.Forms.valueOf(data.get(1)));
            setMaterials(FurnitureSetDB.Materials.valueOf(data.get(2)));
            setStyle(FurnitureSetDB.Styles.valueOf(data.get(3)));
    }


}
