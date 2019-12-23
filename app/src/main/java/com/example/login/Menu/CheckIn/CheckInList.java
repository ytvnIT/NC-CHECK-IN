package com.example.login.Menu.CheckIn;

public class CheckInList {
    private String MAMH;
    private String time;
    private String MAHV;
    public CheckInList(String MAMH, String time, String MAHV){
        this.MAMH = MAMH;
        this.time = time;
        this.MAHV = MAHV;
    }
    public void setMAMH(String MAMH){
        this.MAMH = MAMH;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMAHV() {
        return MAHV;
    }

    public String getMAMH() {
        return MAMH;
    }

    public String getTime() {
        return time;
    }

    public void setMAHV(String MAHV) {
        this.MAHV = MAHV;
    }
}
