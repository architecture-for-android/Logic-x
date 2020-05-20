package com.racofix.things2.m;

interface BaseLogic<V> {

    void attach(V view);

    void detach();

    boolean isViewAttached();

    V getView();

    void onLogicCreated();

    void onLogicDestroy();
}
