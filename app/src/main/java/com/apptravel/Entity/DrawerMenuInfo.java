package com.apptravel.Entity;

/**
 * Created by vungho on 22/12/2016.
 */

public class DrawerMenuInfo {
    private int id;
    private String name;


    public DrawerMenuInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DrawerMenuInfo)) return false;

        DrawerMenuInfo menuInfo = (DrawerMenuInfo) o;

        return getId() == menuInfo.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }


}
