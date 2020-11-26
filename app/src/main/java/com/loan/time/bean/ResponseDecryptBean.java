package com.loan.time.bean;

import java.util.List;

public class ResponseDecryptBean {


    /**
     * regAgreementUrl : https://www.pinganzhiyuan.com/agreement/?packageName=
     * appInfo : {"tabList":[],"surface":"A","entrypoint":" ","showTitle":false,"disablePhysicalKey":false}
     * baseApiUrl : http://dev-sit.panda-fintech.com
     * versionInfo : {"upgradeMsg":"","upgradeUrl":"","forceUpgrade":false,"showUpgradeNotify":false,"nextVersion":"1.2000.0"}
     * extendList : [{"extendCode":"loan_apk_url","extendName":"apk包访问域名url","extendValue":"https://panda-test.s3.cn-northwest-1.amazonaws.com.cn","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_mobile_info_url","extendName":"手机号服务访问域名url","extendValue":"http://52.83.146.25/mif","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_ip_country_url","extendName":"IP定位的访问域名","extendValue":"http://52.83.146.25","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_captcha_url","extendName":"图片验证码访问域名url","extendValue":"https://captcha.panda-fintech.com","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_payment_url","extendName":"支付服务访问域名url","extendValue":"https://dev-sit.panda-fintech.com","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_login_protocol_url","extendName":"登陆协议url","extendValue":"https://dev-sit-ind.panda-fintech.com/html/yndc/agreement/agreements.html","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_data_center_url","extendName":"数据中心访问域名url","extendValue":"https://dev-sit-ind.panda-fintech.com","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_open_api_url","extendName":"open-api访问域名url","extendValue":"http://dev-sit.panda-fintech.com","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_old_image_url","extendName":"图片访问域名url","extendValue":"http://52.83.146.25/aim3/s3.cn-northwest-1.amazonaws.com.cn/panda-test/","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_image_url","extendName":"图片访问域名url","extendValue":"http://52.83.146.25/aim3/s3.cn-northwest-1.amazonaws.com.cn/panda-test","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"testyang","extendName":"测试杨","extendValue":"https://dev-sit-ind.panda-fintech.com/wwww","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_h5_product_detail_url","extendName":"h5产品详情页url","extendValue":"https://dev-sit-ind.panda-fintech.com/html/loanpanda/loanDetailNormal.html?productCode=","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_share_url","extendName":"分享的地址","extendValue":"https://dev-sit-ind.panda-fintech.com/static/lm/danagoshare.html","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"},{"extendCode":"loan_share_url","extendName":"分享的地址","extendValue":"https://dev-sit-ind.panda-fintech.com/static/lm/danagoshare.html","extendType":"biz","extendTypeName":"贷超","extendTypeValue":"loan"}]
     */

    private String regAgreementUrl;
    private AppInfoBean appInfo;
    private String baseApiUrl;
    private VersionInfoBean versionInfo;
    private List<ExtendListBean> extendList;

    public String getRegAgreementUrl() {
        return regAgreementUrl;
    }

    public void setRegAgreementUrl(String regAgreementUrl) {
        this.regAgreementUrl = regAgreementUrl;
    }

    public AppInfoBean getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfoBean appInfo) {
        this.appInfo = appInfo;
    }

    public String getBaseApiUrl() {
        return baseApiUrl;
    }

    public void setBaseApiUrl(String baseApiUrl) {
        this.baseApiUrl = baseApiUrl;
    }

    public VersionInfoBean getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(VersionInfoBean versionInfo) {
        this.versionInfo = versionInfo;
    }

    public List<ExtendListBean> getExtendList() {
        return extendList;
    }

    public void setExtendList(List<ExtendListBean> extendList) {
        this.extendList = extendList;
    }

    public static class AppInfoBean {
        /**
         * tabList : []
         * surface : A
         * entrypoint :
         * showTitle : false
         * disablePhysicalKey : false
         */

        private String surface;
        private String entrypoint;
        private boolean showTitle;
        private boolean disablePhysicalKey;
        private List<?> tabList;

        public String getSurface() {
            return surface;
        }

        public void setSurface(String surface) {
            this.surface = surface;
        }

        public String getEntrypoint() {
            return entrypoint;
        }

        public void setEntrypoint(String entrypoint) {
            this.entrypoint = entrypoint;
        }

        public boolean isShowTitle() {
            return showTitle;
        }

        public void setShowTitle(boolean showTitle) {
            this.showTitle = showTitle;
        }

        public boolean isDisablePhysicalKey() {
            return disablePhysicalKey;
        }

        public void setDisablePhysicalKey(boolean disablePhysicalKey) {
            this.disablePhysicalKey = disablePhysicalKey;
        }

        public List<?> getTabList() {
            return tabList;
        }

        public void setTabList(List<?> tabList) {
            this.tabList = tabList;
        }
    }

    public static class VersionInfoBean {
        /**
         * upgradeMsg :
         * upgradeUrl :
         * forceUpgrade : false
         * showUpgradeNotify : false
         * nextVersion : 1.2000.0
         */

        private String upgradeMsg;
        private String upgradeUrl;
        private boolean forceUpgrade;
        private boolean showUpgradeNotify;
        private String nextVersion;

        public String getUpgradeMsg() {
            return upgradeMsg;
        }

        public void setUpgradeMsg(String upgradeMsg) {
            this.upgradeMsg = upgradeMsg;
        }

        public String getUpgradeUrl() {
            return upgradeUrl;
        }

        public void setUpgradeUrl(String upgradeUrl) {
            this.upgradeUrl = upgradeUrl;
        }

        public boolean isForceUpgrade() {
            return forceUpgrade;
        }

        public void setForceUpgrade(boolean forceUpgrade) {
            this.forceUpgrade = forceUpgrade;
        }

        public boolean isShowUpgradeNotify() {
            return showUpgradeNotify;
        }

        public void setShowUpgradeNotify(boolean showUpgradeNotify) {
            this.showUpgradeNotify = showUpgradeNotify;
        }

        public String getNextVersion() {
            return nextVersion;
        }

        public void setNextVersion(String nextVersion) {
            this.nextVersion = nextVersion;
        }
    }

    public static class ExtendListBean {
        /**
         * extendCode : loan_apk_url
         * extendName : apk包访问域名url
         * extendValue : https://panda-test.s3.cn-northwest-1.amazonaws.com.cn
         * extendType : biz
         * extendTypeName : 贷超
         * extendTypeValue : loan
         */

        private String extendCode;
        private String extendName;
        private String extendValue;
        private String extendType;
        private String extendTypeName;
        private String extendTypeValue;

        public String getExtendCode() {
            return extendCode;
        }

        public void setExtendCode(String extendCode) {
            this.extendCode = extendCode;
        }

        public String getExtendName() {
            return extendName;
        }

        public void setExtendName(String extendName) {
            this.extendName = extendName;
        }

        public String getExtendValue() {
            return extendValue;
        }

        public void setExtendValue(String extendValue) {
            this.extendValue = extendValue;
        }

        public String getExtendType() {
            return extendType;
        }

        public void setExtendType(String extendType) {
            this.extendType = extendType;
        }

        public String getExtendTypeName() {
            return extendTypeName;
        }

        public void setExtendTypeName(String extendTypeName) {
            this.extendTypeName = extendTypeName;
        }

        public String getExtendTypeValue() {
            return extendTypeValue;
        }

        public void setExtendTypeValue(String extendTypeValue) {
            this.extendTypeValue = extendTypeValue;
        }
    }
}
