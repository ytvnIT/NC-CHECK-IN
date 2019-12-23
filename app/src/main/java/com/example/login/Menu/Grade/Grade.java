package com.example.login.Menu.Grade;

import com.google.gson.annotations.SerializedName;

public class Grade {
    @SerializedName("MAMH")
    String MAMH;
    @SerializedName("TCLT")
    int TCLT;
    @SerializedName("TCTH")
    int TCTH;
    @SerializedName("QT")
    float QT;
    @SerializedName("TH")
    float TH;
    @SerializedName("GK")
    float GK;
    @SerializedName("CK")
    float CK;
    @SerializedName("TB")
    float TB;

    public Grade() {
    }

    public String getMamh() {
        return MAMH;
    }

    public void setMamh(String mamh) {
        this.MAMH = mamh;
    }

    public int getTclt() {
        return TCLT;
    }

    public void setTclt(int tclt) {
        this.TCLT = tclt;
    }

    public int getTcth() {
        return TCTH;
    }

    public void setTcth(int tcth) {
        this.TCTH = tcth;
    }

    public float getQt() {
        return QT;
    }

    public void setQt(float qt) {
        this.QT = qt;
    }

    public float getGk() {
        return GK;
    }

    public void setGk(float gk) {
        this.GK = gk;
    }

    public float getTh() {
        return TH;
    }

    public void setTh(float th) {
        this.TH = th;
    }

    public float getCk() {
        return CK;
    }

    public void setCk(float ck) {
        this.CK = ck;
    }

    public float getTb() {
        return TB;
    }

    public void setTb(float tb) {
        this.TB = tb;
    }

    public Grade(String mamh, int tclt, int tcth, float qt, float gk, float th, float ck, float tb) {
        this.MAMH = mamh;
        this.TCLT = tclt;
        this.TCTH = tcth;
        this.QT = qt;
        this.GK = gk;
        this.TH = th;
        this.CK = ck;
        this.TB = tb;
    }
}
