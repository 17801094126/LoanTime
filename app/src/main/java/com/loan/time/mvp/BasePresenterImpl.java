package com.loan.time.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V>{

    //Viewceng层接口的引用
    protected Reference<V> mViewRef;

    //View层接口弱引用
    @Override
    public void attachView(V view) {
        mViewRef=new WeakReference<V>(view);
    }
    //获取View层接口
    protected V getView(){
        return mViewRef.get();
    }
    //判断View是否为空
    public boolean isAttarchView(){
        return mViewRef!=null&&mViewRef.get()!=null;
    }
    //用来关联View层的生命周期，看View层是否已经销毁
    public void detachView(){
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef=null;
        }
    }
}
