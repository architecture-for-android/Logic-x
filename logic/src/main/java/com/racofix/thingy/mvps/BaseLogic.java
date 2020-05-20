package com.racofix.thingy.mvps;

interface BaseLogic<V> {

    void attach(V view);

    void detach();

    boolean isViewAttached();

    V getView();

    void onLogicCreated();

    void onLogicDestroy();
}
