package com.stepone.uikit.dispatcher.interceptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.stepone.uikit.dispatcher.RouterMap;
import com.stepone.uikit.dispatcher.request.PushRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: PushInterceptorCenter
 * Author: y.liang
 * Date: 2019-12-02 12:35
 */

final class PushInterceptorCenter extends InterceptorCenter<PushRequest> {
    private static PushInterceptorCenter instance = null;
    static PushInterceptorCenter getInstance() {
        if (instance == null) {
            synchronized (PushInterceptorCenter.class) {
                if (instance == null) {
                    instance = new PushInterceptorCenter();
                }
            }
        }

        return instance;
    }

    @Override
    protected List<Interceptor<PushRequest>> constructDefaultInterceptors() {
        List<Interceptor<PushRequest>> list = new ArrayList<>();
        list.add(new FinallyInterceptor());

        return list;
    }

    private final static class FinallyInterceptor implements Interceptor<PushRequest> {

        @Override
        public int priority() {
            return Integer.MAX_VALUE;
        }

        @Override
        public String name() {
            return "finallyPushInterceptor";
        }

        @Override
        public void interceptor(Chain<PushRequest> chain) {
            PushRequest request = chain.getRequest();

            final Context context = request.getContext();
            RouterMap.Entry entry = request.getPayload();
            if (context != null && entry != null) {
                Class pageClazz = entry.getTargetClazz();
                if (pageClazz != null) {
                    Class activityClazz = null;
                    if (Fragment.class.isAssignableFrom(pageClazz)) {
                        activityClazz = entry.getParentClazz();
                    } else if (Activity.class.isAssignableFrom(pageClazz)) {
                        activityClazz = pageClazz;
                    }

                    final Intent intent = new Intent(context, activityClazz);
                    if (!(context instanceof Activity)) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }

                    final Bundle params = request.getBundle();
                    if (params != null) {
                        intent.putExtras(params);
                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ActivityCompat.startActivity(context, intent, params);
                        }
                    });

                }
            }
        }
    }
}
