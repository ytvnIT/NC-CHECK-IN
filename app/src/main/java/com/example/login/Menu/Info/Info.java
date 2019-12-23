package com.example.login.Menu.Info;

public class Info {
    String EMAIL;
    String NGSINH;
    String GIOITINH;
    String NOISINH;
    String MALOP;

    public Info() {
    }

    public Info(String EMAIL, String NGSINH, String GIOITINH, String NOISINH, String MALOP) {
        this.EMAIL = EMAIL;
        this.NGSINH = NGSINH;
        this.GIOITINH = GIOITINH;
        this.NOISINH = NOISINH;
        this.MALOP = MALOP;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getNGSINH() {
        return NGSINH;
    }

    public void setNGSINH(String NGSINH) {
        this.NGSINH = NGSINH;
    }

    public String getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public String getNOISINH() {
        return NOISINH;
    }

    public void setNOISINH(String NOISINH) {
        this.NOISINH = NOISINH;
    }

    public String getMALOP() {
        return MALOP;
    }

    public void setMALOP(String MALOP) {
        this.MALOP = MALOP;
    }
}
