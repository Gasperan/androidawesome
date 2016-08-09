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

        }
        return categories;
    }
}
