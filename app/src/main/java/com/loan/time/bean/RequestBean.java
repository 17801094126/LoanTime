package com.loan.time.bean;

import android.annotation.SuppressLint;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.loan.time.App;
import com.loan.time.api.Constants;
import com.loan.time.utils.AppUtils;

import java.io.Serializable;

/**
 * @author renji
 * @date 2017/12/28
 * 所有的请求参数
 */

public class RequestBean implements Serializable {

    private String packageName= Constants.PaackageName;
    private String channelKey=Constants.ChannelKey;
    private String platform=Constants.Platform;
    //每次打开App时候传 ""，服务器返回后保存，本次生命周期(App放置到后台有唤醒事件时候，需要重新获取)内一直使用
    private String deviceToken;
    //第一次传空，服务器返回后一直保存在本地，每次请求都使用
    private String deviceId;
    //未登录用户传 0 登录后保存服务器返回的 uid 并一直使用，退出后删除本地存储
    private String uid;
    private String versionKey= Constants.VersionKey;
    private String version=Constants.VersionCode;
    //采集的设备信息
    private DataBean deviceInfo;
    //印尼 +62/越南 +84 /印度 +91 (get请求时要做UrlEncode)
    private String country="+91";

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVersionKey() {
        return versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DataBean getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DataBean deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static class DataBean {
        //Android 设备安装的唯一ID，Settings.System.ANDROID_ID
        private String androidId= Settings.System.ANDROID_ID;
        //可用内存
        private String availMemory= String.valueOf(AppUtils.getFreeMem(App.context));
        // 固件名称, Build.VERSION.CODENAME
        private String codeName= Build.VERSION.CODENAME;
        // 手机 CPU 架构，Build.CPU_ABI
        private String cpu=Build.CPU_ABI;
        // CPU 信息
        private String cpuInfo=AppUtils.getCpuName();
        // 设备的软件版本号, TelephonyManager.getDeviceSoftwareVersion
        private String deviceSoftwareVersion;
        // 屏幕分辨率, 设备宽|设备高
        private String display;
        // 基站定位信息, cid|lac
        private String gsmCellLocation;
        // Build.HARDWARD
        private String hardware;
        // 手机 IMEI 号 TelphonyManager.getDeviceId()
        private String imei;
        // 手机 IMSI 号 TelphonyManager.getSubscriberId()
        private String imsi;
        // 语言代码, Locale.getDefault().getLanguage()
        private String language;
        // 硬件制造商, Build.MANUFACTURER
        private String manufacturer=Build.MANUFACTURER;
        // 设备型号, Build.MODEL
        private String model=Build.MODEL;
        //网路运营商,TelphonyManager.getNetworkOperator()
        private String networkOperator;
        //网路运营商名称(SPN),TelphonyManager.getNetworkOperatorName()
        private String networkOperatorName;
        // 网路状态,TelphonyManager.getNetworkType()
        private String networkType;
        // 产品的名称,Build.PRODUCT
        private String product=Build.PRODUCT;
        //无线电固件版本,Build.getRadioVersion()
        private String radioVersion=Build.getRadioVersion();
        // Android SDK 版本名称,Build.VERSION.RELEASE
        private String release=Build.VERSION.RELEASE;
        // Android SDK 版本号,Build.VERSION.SDK_INT
        private String sdkVersion= String.valueOf(Build.VERSION.SDK_INT);
        // 设备序列号,Build.SERIAL
        private String serialNumber=Build.SERIAL;
        // 总内存
        private String totalMemory;
        // uuid,使用设备硬件信息生成一个唯一的id
        private String uuid;

        public String getAndroidId() {
            return androidId;
        }

        public void setAndroidId(String androidId) {
            this.androidId = androidId;
        }

        public String getAvailMemory() {
            return availMemory;
        }

        public void setAvailMemory(String availMemory) {
            this.availMemory = availMemory;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }

        public String getCpu() {
            return cpu;
        }

        public void setCpu(String cpu) {
            this.cpu = cpu;
        }

        public String getCpuInfo() {
            return cpuInfo;
        }

        public void setCpuInfo(String cpuInfo) {
            this.cpuInfo = cpuInfo;
        }

        public String getDeviceSoftwareVersion() {
            return deviceSoftwareVersion;
        }

        public void setDeviceSoftwareVersion(String deviceSoftwareVersion) {
            this.deviceSoftwareVersion = deviceSoftwareVersion;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getGsmCellLocation() {
            return gsmCellLocation;
        }

        public void setGsmCellLocation(String gsmCellLocation) {
            this.gsmCellLocation = gsmCellLocation;
        }

        public String getHardware() {
            return hardware;
        }

        public void setHardware(String hardware) {
            this.hardware = hardware;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getImsi() {
            return imsi;
        }

        public void setImsi(String imsi) {
            this.imsi = imsi;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getNetworkOperator() {
            return networkOperator;
        }

        public void setNetworkOperator(String networkOperator) {
            this.networkOperator = networkOperator;
        }

        public String getNetworkOperatorName() {
            return networkOperatorName;
        }

        public void setNetworkOperatorName(String networkOperatorName) {
            this.networkOperatorName = networkOperatorName;
        }

        public String getNetworkType() {
            return networkType;
        }

        public void setNetworkType(String networkType) {
            this.networkType = networkType;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getRadioVersion() {
            return radioVersion;
        }

        public void setRadioVersion(String radioVersion) {
            this.radioVersion = radioVersion;
        }

        public String getRelease() {
            return release;
        }

        public void setRelease(String release) {
            this.release = release;
        }

        public String getSdkVersion() {
            return sdkVersion;
        }

        public void setSdkVersion(String sdkVersion) {
            this.sdkVersion = sdkVersion;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getTotalMemory() {
            return totalMemory;
        }

        public void setTotalMemory(String totalMemory) {
            this.totalMemory = totalMemory;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }


}
