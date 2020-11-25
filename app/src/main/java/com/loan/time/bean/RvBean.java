package com.loan.time.bean;

import android.graphics.drawable.Drawable;

public class RvBean {

    private Drawable drawable;
    private String title;
    private String content;

    public RvBean(Drawable drawable, String title, String content) {
        this.drawable = drawable;
        this.title = title;
        this.content = content;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
