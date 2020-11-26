package com.loan.time.bean;

import android.provider.ContactsContract;

import com.loan.time.BuildConfig;
import com.loan.time.api.Constants;

public class DecryptBean {

    /**
     * packageName :
     * version :
     * channelKey :
     * versionKey :
     */

    private String packageName= BuildConfig.APPLICATION_ID;
    private String version= Constants.VersionCode;
    private String channelKey= Constants.ChannelKey;
    private String versionKey=BuildConfig.VERSION_NAME;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public String getVersionKey() {
        return versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }
}
