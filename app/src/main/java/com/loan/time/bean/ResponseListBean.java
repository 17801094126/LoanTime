package com.loan.time.bean;

import java.io.Serializable;
import java.util.List;

public class ResponseListBean {

    private String code;
    private String message;
    private List<DataBean> productList;

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

    public List<DataBean> getData() {
        return productList;
    }

    public void setData(List<DataBean> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "ResponseListBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", productList=" + productList +
                '}';
    }

    public static class DataBean implements Serializable {
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


}
