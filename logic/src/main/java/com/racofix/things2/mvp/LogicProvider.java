package com.racofix.things2.mvp;

import android.util.Log;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

class LogicProvider {

    private static volatile LogicProvider logicProvider;
    private Map<Object, HashSet<BaseLogic>> logicCaches = new LinkedHashMap<>();

    static LogicProvider getInstance() {
        if (logicProvider == null) {
            synchronized (LogicProvider.class) {
                if (logicProvider == null) {
                    logicProvider = new LogicProvider();
                }
            }
        }
        return logicProvider;
    }

    <V> void put(V view) {
        LogicArr logicArr = view.getClass().getAnnotation(LogicArr.class);
        if (logicArr == null) return;

        if (logicCaches.containsKey(view)) return;
        HashSet<BaseLogic> logics = new HashSet<>();

        Class[] classes = logicArr.value();
        for (Class clazz : classes) {
            try {
                BaseLogic<V> baseLogic = Util.castTo(clazz.newInstance());
                baseLogic.bindView(view);
                baseLogic.onLogicCreated();
                logics.add(baseLogic);

            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        if (logics.isEmpty()) return;

        Log.d(getClass().getSimpleName(), view.getClass().getSimpleName() + " Init LogicArr >>\n" + logics.toString());
        logicCaches.put(view, logics);
    }

    <V, T extends BaseLogic> T get(V view, Class<T> clazz) {
        HashSet<BaseLogic> logics = logicCaches.get(view);
        if (logics == null || logics.isEmpty()) {
            throw new NullPointerException(view.getClass().getName() + " @LogicArr is empty.");
        }

        T baseLogic = null;
        for (BaseLogic logic : logics) {
            String logicName = logic.getClass().getName();
            if (logicName.equals(clazz.getName())) {
                baseLogic = Util.castTo(logic);
                break;
            }
        }

        Log.d(getClass().getName(), view.getClass().getSimpleName() + " LogicImpl " + baseLogic.toString());
        return baseLogic;
    }

    <V> void remove(V view) {
        HashSet<BaseLogic> logics = logicCaches.get(view);
        if (logics == null || logics.isEmpty()) {
            return;
        }
        for (BaseLogic logic : logics) {
            logic.unbindView();
            logic.onLogicDestroy();
        }

        Log.d(getClass().getSimpleName(), view.getClass().getSimpleName() + " Remove LogicArr >>\n" + logics.toString());
        logicCaches.remove(view);
    }
}
