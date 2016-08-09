package com.nisum.storelayout.data;

import java.util.ArrayList;

/**
 * Created by nisum on 8/9/16.
 */
public class FakeDataManager implements DataManager {
    private ArrayList<CategoryDO> categories;

    @Override
    public ArrayList<CategoryDO> getCategories() {
        if(null == categories){
            categories = new ArrayList<CategoryDO>();
            categories.add(new CategoryDO((byte) 1, "Electro"));
            categories.add(new CategoryDO((byte) 2, "Tecnología"));
            categories.add(new CategoryDO((byte) 3, "Línea Blanca"));
            categories.add(new CategoryDO((byte) 4, "Dormitorio"));
            categories.add(new CategoryDO((byte) 5, "Muebles"));
            categories.add(new CategoryDO((byte) 6, "Deco"));
            categories.add(new CategoryDO((byte) 7, "Moda"));
            categories.add(new CategoryDO((byte) 8, "Calzado"));
            categories.add(new CategoryDO((byte) 9, "Belleza"));
            categories.add(new CategoryDO((byte) 10, "Desportes"));
            categories.add(new CategoryDO((byte) 11, "Infantil"));
            categories.add(new CategoryDO((byte) 12, "Juguetes"));
        }
        return categories;
    }
}
