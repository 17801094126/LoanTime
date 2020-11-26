package com.loan.time.bean;

import java.io.Serializable;

public class ResponseBean implements Serializable {


    @Override
    public String toString() {
        return "ResponseBean{" +
                "code:'" + code + '\'' +
                ", message:'" + message + '\'' +
                ", data:" + data +
                '}';
    }

    /**
     * code : 100000
     * message : 处理成功
     * data : {"id":"动态码ID，登录时使用","image":"动态码图片64的，转成bitmap显示"}
     */

    private String code;
    private String message;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        /**
         * id : 动态码ID，登录时使用
         * image : 动态码图片64的，转成bitmap显示
         */

        private String id;
        private String image;
        private String deviceId;
        private String deviceToken;
        private String config;
        private String uid;
        private String token;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getConfig() {
            return config;
        }

        public void setConfig(String config) {
            this.config = config;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", image='" + image + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    ", deviceToken='" + deviceToken + '\'' +
                    ", config='" + config + '\'' +
                    ", uid='" + uid + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
