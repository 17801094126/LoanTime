package com.loan.time.bean;

public class HeaderBean {
    private String token;

    public HeaderBean(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
