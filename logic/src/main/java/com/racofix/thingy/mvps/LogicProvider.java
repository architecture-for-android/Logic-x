package com.racofix.thingy.mvps;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

class LogicProvider {

    private static volatile LogicProvider logicProvider;
    private Map<String, HashSet<BaseLogic>> logicCaches = new LinkedHashMap<>();

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

        String viewKey = view.getClass().getName();
        HashSet<BaseLogic> logics = logicCaches.get(viewKey);
        if (logics == null) {
            logics = new HashSet<>();
        }

        Class[] classes = logicArr.value();
        for (Class clazz : classes) {
            try {
                BaseLogic<V> baseLogic = Util.castTo(clazz.newInstance());
                baseLogic.attach(view);
                baseLogic.onLogicCreated();
                logics.add(baseLogic);

            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        if (logics.isEmpty()) return;
        logicCaches.put(viewKey, logics);
    }

    <V, T extends BaseLogic> T get(V view, Class<T> clazz) {
        String viewKey = view.getClass().getName();
        HashSet<BaseLogic> logics = logicCaches.get(viewKey);
        if (logics == null || logics.isEmpty()) {
            throw new NullPointerException(viewKey + " @LogicArr is empty.");
        }

        T baseLogic = null;
        for (BaseLogic logic : logics) {
            String logicName = logic.getClass().getName();
            if (logicName.equals(clazz.getName())) {
                baseLogic = Util.castTo(logic);
                break;
            }
        }

        return baseLogic;
    }

    <V> void remove(V view) {
        String viewKey = view.getClass().getName();
        HashSet<BaseLogic> logics = logicCaches.get(viewKey);
        if (logics == null || logics.isEmpty()) {
            return;
        }

        for (BaseLogic logic : logics) {
            logic.detach();
            logic.onLogicDestroy();
        }

        logicCaches.remove(viewKey);
    }
}
