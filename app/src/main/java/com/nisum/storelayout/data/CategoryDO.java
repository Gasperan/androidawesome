package com.nisum.storelayout.data;

/**
 * Created by nisum on 8/9/16.
 */
public class CategoryDO {
    private String name;
    private byte cod;

    public CategoryDO(byte cod, String name){
        this.cod = cod;
        this.name = name;
    }

    public byte getCod() {
        return cod;
    }

    public void setCod(byte cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
