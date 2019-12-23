package com.example.login.Menu.TKB;

import com.google.gson.annotations.SerializedName;

public class TKB {
    @SerializedName("THU")
    private int id;
    @SerializedName("TENMH")
    private String mamh;
    @SerializedName("HOTEN")
    private String name;
    @SerializedName("TIET")
    private String tiet;
    @SerializedName("PHONG")
    private String phong;
    public TKB(){

    }

    public TKB(int id, String mamh, String name, String tiet, String phong) {
        this.id = id;
        this.mamh = mamh;
        this.name = name;
        this.tiet = tiet;
        this.phong = phong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMamh() {
        return mamh;
    }

    public void setMamh(String mamh) {
        this.mamh = mamh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTiet() {
        return tiet;
    }

    public void setTiet(String tiet) {
        this.tiet = tiet;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }
}