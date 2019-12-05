package com.stepone.component.navigator.interceptor;

import androidx.annotation.NonNull;

import com.stepone.component.navigator.request.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * FileName: Interceptor
 * Author: shiliang
 * Date: 2019-11-30 19:59
 */
public interface Interceptor<T extends Request> {

    int priority();
    String name();
    void intercept(Chain<T> chain);

    /**
     * 拦截器按照优先级排序
     */
    final class Chain<T extends Request> {
        private final List<Interceptor<T>> interceptors;
        private Iterator<Interceptor<T>> iterator;
        private final T request;

        Chain(@NonNull List<Interceptor<T>> interceptors, @NonNull T request) {
            this.interceptors = new ArrayList<>(interceptors);
            this.request = request;
            iterator = this.interceptors.iterator();

            Collections.sort(this.interceptors, new Comparator<Interceptor>() {
                @Override
                public int compare(Interceptor o1, Interceptor o2) {
                    return o1.priority() - o2.priority();
                }
            });
        }

        public T getRequest() {
            return this.request;
        }

        public void proceed() {
            if (iterator.hasNext()) {
                iterator.next().intercept(this);
            }
        }

        public void cancel() {
            if (request.getObserver() != null) {
                request.getObserver().onIntercepted(request);
            }
        }

    }
}
