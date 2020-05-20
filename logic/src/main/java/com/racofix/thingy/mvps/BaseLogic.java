package com.racofix.thingy.mvps;

interface BaseLogic<V> {

    void bindView(V view);

    void unbindView();

    boolean isViewBind();

    V getView();

    void onLogicCreated();

    void onLogicDestroy();
}
