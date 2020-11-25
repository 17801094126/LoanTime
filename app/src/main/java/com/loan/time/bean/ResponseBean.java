package com.loan.time.bean;

import java.io.Serializable;

public class ResponseBean implements Serializable {

    private String code;
    private DataBean data;
    private String message;

    public static class DataBean implements Serializable {


    }

}
