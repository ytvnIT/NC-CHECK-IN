package com.example.login.Login;


public class Result {
    private String TEN;
    private String ANH;
    private String status;

    public Result(String TEN, String ANH, String status) {
        this.TEN = TEN;
        this.ANH = ANH;
        this.status = status;
    }

    public String getTEN() {
        return TEN;
    }

    public String getANH() {
        return ANH;
    }

    public String getStatus() {
        return status;
    }

    public void setTEN(String TEN) {
        this.TEN = TEN;
    }

    public void setANH(String ANH) {
        this.ANH = ANH;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

