package com.loan.time.bean;

import java.io.Serializable;

/**
 * @author renji
 * @date 2017/12/28
 * 所有的请求参数
 */

public class RequestBean implements Serializable {

    private String mechanismNumber;
    private String channelNumber;
    private DataBean data;

    public String getMechanismNumber() {
        return mechanismNumber;
    }

    public void setMechanismNumber(String mechanismNumber) {
        this.mechanismNumber = mechanismNumber;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {


    }
}
