package com.racofix.things2.mvp;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BaseLogicImpl<V> implements BaseLogic<V> {

    private V viewProxy;
    private WeakReference<V> weakReference;

    @Override
    public void bindView(final V view) {
        weakReference = new WeakReference<>(view);
        viewProxy = Util.castTo(
                Proxy.newProxyInstance(
                        view.getClass().getClassLoader(),
                        view.getClass().getInterfaces(),
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                if (isViewBind())
                                    return method.invoke(weakReference.get(), args);
                                return null;
                            }
                        }));
    }

    @Override
    public boolean isViewBind() {
        return weakReference != null && weakReference.get() != null;
    }

    @Override
    public V getView() {
        return viewProxy;
    }

    @Override
    public void unbindView() {
        if (isViewBind()) {
            weakReference.clear();
            weakReference = null;
        }
    }

    @Override
    public void onLogicCreated() {
    }

    @Override
    public void onLogicDestroy() {
    }
}
