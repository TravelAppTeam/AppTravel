package com.apptravel.Models;

/**
 * Created by Le on 22-Dec-16.
 */

public class Travel {
    private String DiaChi;
    private String Mota;
    private String Tel;
    private String Ten;
    private String img;

    public Travel() {

    }

    public Travel(String diachi, String mota, String tel, String ten, String img) {
        this.DiaChi = diachi;
        this.Mota = mota;
        Tel = tel;
        Ten = ten;
        this.img = img;
    }


    public void setDiaChi(String diaChi) {
        this.DiaChi = diaChi;
    }

    public void setMota(String mota) {
        this.Mota = mota;
    }

    public void setTel(String tel) {
        Tel = tel;
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

    public String getTel() {
        return Tel;
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
                ", Tel='" + Tel + '\'' +
                ", Ten='" + Ten + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
