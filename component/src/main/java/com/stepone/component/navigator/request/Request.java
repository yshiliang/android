package com.stepone.component.navigator.request;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.stepone.component.common.ActivityHooker;
import com.stepone.component.navigator.Navigator;
import com.stepone.component.navigator.RouterMap;

/**
 * FileName: Request
 * Author: shiliang
 * Date: 2019-11-30 19:59
 */


/**
 * 路由请求
 *
 * 采用链式方式构造路由请求对象
 */

public abstract class Request {
    /**
     * request携带的上下文信息
     */
    private Context context;
    private Bundle bundle;
    private Observer observer;
    private Callback callback;

    /**
     * 路由匹配的关键信息
     */
    private String group;//根据分组查询路由，默认分组为空字符串
    private String path;

    /**
     * 匹配到的路由信息
     */
    private RouterMap.MetaRouter metaRouter;

    /**
     * 通过解析uri，匹配到合适的路由
     */
    private Uri uri;

    Request() {
        this.bundle = new Bundle();
    }

    public interface Callback {
        void onFailure();
        void onSucceed();
    }

    protected void call() {
        Navigator.call(this);
    }

    public boolean isValid() {
        return true;
    }

    public void push() {}

    public void pushForResult(ActivityHooker.OnActivityResultCallback callback) {}

    public void back() {}

    public void back(Callback callback) {}

    /**
     * 路由请求过程的观察者，在当前线程回调
     */
    public interface Observer {
        void onLost(Request request);
        void onFound(Request request);
        void onIntercepted(Request request);
        void onArrival(Request request);
    }

    /**
     * getter && setter
     */
    public Context getContext() {
        return context;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public Observer getObserver() {
        return observer;
    }

    public Callback getCallback() {
        return callback;
    }

    public String getGroup() {
        return group;
    }

    public String getPath() {
        return path;
    }

    public RouterMap.MetaRouter getMetaRouter() {
        return metaRouter;
    }

    public Uri getUri() {
        return uri;
    }

    void setContext(Context context) {
        this.context = context;
    }

    void setBundle(Bundle bundle) {
        if (bundle != null) {
            this.bundle = bundle;
        }
    }

    void setObserver(Observer observer) {
        this.observer = observer;
    }

    void setGroup(String group) {
        this.group = group;
    }

    void setPath(String path) {
        this.path = path;
    }

    void setMetaRouter(RouterMap.MetaRouter metaRouter) {
        this.metaRouter = metaRouter;
    }

    void setUri(Uri uri) {
        this.uri = uri;
    }

    /**
     * 链式调用方式设置参数
     */

    public Request from(Context context) {
        this.context = context;
        return this;
    }

    public Request fillRouterInfo(RouterMap.MetaRouter metaRouter) {
        this.metaRouter = metaRouter;
        return this;
    }

    public Request withGroup(String group) {
        this.group = group;
        return this;
    }

    public Request withBundle(Bundle bundle) {
        if (bundle != null) {
            this.bundle.putAll(bundle);
        }
        return this;
    }

    public Request withObserver(Observer observer) {
        this.observer = observer;
        return this;
    }

    public Request withCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public Request withInt(String key, int i) {
        bundle.putInt(key, i);
        return this;
    }

    public Request withLong(String key, long l) {
        bundle.putLong(key, l);
        return this;
    }

    public Request withFloat(String key, float f) {
        bundle.putFloat(key, f);
        return this;
    }

    public Request withDouble(String key, double d) {
        bundle.putDouble(key, d);
        return this;
    }

    public Request withString(String key, String str) {
        bundle.putString(key, str);
        return this;
    }

    public Request withChar(String key, char ch) {
        bundle.putChar(key, ch);
        return this;
    }
}
