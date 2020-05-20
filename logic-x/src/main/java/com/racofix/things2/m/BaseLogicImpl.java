package com.racofix.things2.m;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BaseLogicImpl<V> implements BaseLogic<V> {

    private V viewProxy;
    private WeakReference<V> weakReference;

    @Override
    public void attach(final V view) {
        weakReference = new WeakReference<>(view);
        viewProxy = Util.castTo(
                Proxy.newProxyInstance(
                        view.getClass().getClassLoader(),
                        view.getClass().getInterfaces(),
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                if (isViewAttached())
                                    return method.invoke(weakReference.get(), args);
                                return null;
                            }
                        }));
    }

    @Override
    public void detach() {
        if (isViewAttached()) {
            weakReference.clear();
            weakReference = null;
        }
    }

    @Override
    public boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
    }

    @Override
    public V getView() {
        return viewProxy;
    }

    @Override
    public void onLogicCreated() {
    }

    @Override
    public void onLogicDestroy() {
    }
}
