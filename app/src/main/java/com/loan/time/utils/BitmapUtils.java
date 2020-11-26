package com.loan.time.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

public class BitmapUtils {

    public static boolean isBase64Img(String imgurl){
        if(!TextUtils.isEmpty(imgurl)&&(imgurl.startsWith("data:image/png;base64,")
                ||imgurl.startsWith("data:image/*;base64,")||imgurl.startsWith("data:image/jpg;base64,")
        ))
        {
            return true;
        }
        return false;
    }

    /**
     * 将Base64格式字符串转换为位图
     * @param encodeString 图片的Base64格式字符串
     * @return 位图
     */
    public static Bitmap stringToBitmap(String encodeString) {
        byte[] bytes = Base64.decode(encodeString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
