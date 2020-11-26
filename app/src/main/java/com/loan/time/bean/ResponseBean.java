package com.loan.time.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

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


        public static class ProductListBean implements Parcelable {
            //产品Id
            private String productId;
            //产品名称
            private String title;
            //logo地址
            private String logo;
            //产品币种
            private String currency;
            //最大可接额度
            private String maxBorrowAmt;
            //最长借款期限
            private String maxLoanTerm;
            //最小借款期限
            private String minLoanTerm;
            //日利率
            private String interestCycleRate;
            //立即申请跳转地址
            private String innerProductUrl;
            //状态 publish=正常 uv_partner_limit=名额已满 applied=今日已申请
            private String status;
            //产品说明
            private String productIntroduction;

            protected ProductListBean(Parcel in) {
                productId = in.readString();
                title = in.readString();
                logo = in.readString();
                currency = in.readString();
                maxBorrowAmt = in.readString();
                maxLoanTerm = in.readString();
                minLoanTerm = in.readString();
                interestCycleRate = in.readString();
                innerProductUrl = in.readString();
                status = in.readString();
                productIntroduction = in.readString();
            }

            public static final Creator<ProductListBean> CREATOR = new Creator<ProductListBean>() {
                @Override
                public ProductListBean createFromParcel(Parcel in) {
                    return new ProductListBean(in);
                }

                @Override
                public ProductListBean[] newArray(int size) {
                    return new ProductListBean[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(productId);
                dest.writeString(title);
                dest.writeString(logo);
                dest.writeString(currency);
                dest.writeString(maxBorrowAmt);
                dest.writeString(maxLoanTerm);
                dest.writeString(minLoanTerm);
                dest.writeString(interestCycleRate);
                dest.writeString(innerProductUrl);
                dest.writeString(status);
                dest.writeString(productIntroduction);
            }

            public String getProductIntroduction() {
                return productIntroduction;
            }

            public void setProductIntroduction(String productIntroduction) {
                this.productIntroduction = productIntroduction;
            }

            @Override
            public String toString() {
                return "DataBean{" +
                        "productId='" + productId + '\'' +
                        ", title='" + title + '\'' +
                        ", logo='" + logo + '\'' +
                        ", currency='" + currency + '\'' +
                        ", maxBorrowAmt='" + maxBorrowAmt + '\'' +
                        ", maxLoanTerm='" + maxLoanTerm + '\'' +
                        ", minLoanTerm='" + minLoanTerm + '\'' +
                        ", interestCycleRate='" + interestCycleRate + '\'' +
                        ", innerProductUrl='" + innerProductUrl + '\'' +
                        ", status='" + status + '\'' +
                        '}';
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getMaxBorrowAmt() {
                return maxBorrowAmt;
            }

            public void setMaxBorrowAmt(String maxBorrowAmt) {
                this.maxBorrowAmt = maxBorrowAmt;
            }

            public String getMaxLoanTerm() {
                return maxLoanTerm;
            }

            public void setMaxLoanTerm(String maxLoanTerm) {
                this.maxLoanTerm = maxLoanTerm;
            }

            public String getMinLoanTerm() {
                return minLoanTerm;
            }

            public void setMinLoanTerm(String minLoanTerm) {
                this.minLoanTerm = minLoanTerm;
            }

            public String getInterestCycleRate() {
                return interestCycleRate;
            }

            public void setInterestCycleRate(String interestCycleRate) {
                this.interestCycleRate = interestCycleRate;
            }

            public String getInnerProductUrl() {
                return innerProductUrl;
            }

            public void setInnerProductUrl(String innerProductUrl) {
                this.innerProductUrl = innerProductUrl;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }


        }
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
        private List<ProductListBean> productList;

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

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
                    ", productList=" + productList +
                    '}';
        }
    }
}
