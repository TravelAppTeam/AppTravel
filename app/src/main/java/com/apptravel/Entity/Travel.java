package com.apptravel.Entity;

import java.io.Serializable;

/**
 * Created by Le on 22-Dec-16.
 */

public class Travel implements Serializable {
    private String id;
    private String Ten;
    private String DiaChi;
    private String Mota;
    private String Contact;
    private String img;
    private String lowimg;
    private String Map;
    private int view;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLowimg() {
        return lowimg;
    }

    public void setLowimg(String lowimg) {
        this.lowimg = lowimg;
    }

    public String getMap() {
        return Map;
    }

    public void setMap(String map) {
        Map = map;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Travel(String id, String ten, String diaChi, String mota, String contact, String img, String lowimg, String map, int view) {
        this.id = id;
        Ten = ten;
        DiaChi = diaChi;
        Mota = mota;
        Contact = contact;
        this.img = img;
        this.lowimg = lowimg;
        Map = map;
        this.view = view;
    }

    public Travel() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Travel travel = (Travel) o;

        return Ten != null ? Ten.equals(travel.Ten) : travel.Ten == null;

    }

    @Override
    public int hashCode() {
        return Ten != null ? Ten.hashCode() : 0;
    }

    public void setDiaChi(String diaChi) {
        this.DiaChi = diaChi;
    }

    public void setMota(String mota) {
        this.Mota = mota;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getDiaChi() {
        return DiaChi;
    }

    public String getMota() {
        return Mota;
    }

    public String getContact() {
        return Contact;
    }

    public String getTen() {
        return Ten;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "Travel{" +
                ", DiaChi='" + DiaChi + '\'' +
                ", Mota='" + Mota + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Ten='" + Ten + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
