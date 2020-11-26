package com.loan.time.network;

import android.content.Context;

import com.loan.time.R;

import goutil.Goutil;

public class HttpUtils {
    private static HttpUtils httpUtils;
    /**
     * 实例化HttpUtils
     */
    public static HttpUtils getInstance() {
        if (httpUtils==null){
            synchronized (HttpUtils.class){
                if (httpUtils==null){
                    /*创建retrofit对象*/
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    /**
     *
     * @param envURL  BaseURl
     * @param apiCode 接口文档有
     * @param body 请求体
     * @param headers 接口文档有
     * @return
     */
    public String sendRequest(String envURL,String apiCode,String body,String headers){
        return Goutil.sendRequest(envURL, String.valueOf(R.string.app_name), apiCode, body, headers);
    }

    /**
     *
     * @param envURL  BaseURl
     * @param apiCode 接口文档有
     * @param body 请求体
     * @param headers 接口文档有
     * @param timgout 超时时间
     * @return
     */
    public String sendRequestWithTimeout(String envURL,String apiCode,String body,String headers,long timgout){
        return Goutil.sendRequestWithTimeout(envURL, String.valueOf(R.string.app_name), apiCode, body, headers, timgout);
    }

    /**
     *数据解密 Decrypt
     * @param config App配置,共4个参数,json形式字符串,如: {"packageName":"","version":"","channelKey":"","versionKey":""}
     * @param plain base64 标准编码密文
     * @return
     */
    public byte[] decrypt(String config,String plain) throws Exception {
        return Goutil.decrypt(config,plain);
    }

    /**
     * 数据加密 Encrypt
     * @param config App配置,共4个参数,json形式字符串,如: {"packageName":"","version":"","channelKey":"","versionKey":""}
     * @param ciphertext 明文 byte 数组
     * @return
     */
    public String encrypt(String config,byte[] ciphertext) throws Exception {
        return Goutil.encrypt(config,ciphertext);
    }


}
