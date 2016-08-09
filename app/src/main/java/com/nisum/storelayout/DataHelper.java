package com.nisum.storelayout;

import com.nisum.storelayout.data.CategoryDO;
import com.nisum.storelayout.data.DataManager;
import com.nisum.storelayout.data.FakeDataManager;

import java.util.ArrayList;

/**
 * Created by nisum on 8/9/16.
 */
public class DataHelper {
    private static DataManager dataManager;

    private DataHelper(){}

    public static DataManager getDataManager(){
        if(null == dataManager){
            dataManager = new FakeDataManager();
        }
        return dataManager;
    }

    public static ArrayList<CategoryDO> getCategories(){
        return getDataManager().getCategories();
    }
}
