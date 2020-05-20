package com.racofix.thingy.mvps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseLogicActivity extends AppCompatActivity {

    private LogicProvider logicProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logicProvider = LogicProvider.getInstance();
        logicProvider.put(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logicProvider.remove(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseLogic> T getLogic(Class<T> clazz) {
        return logicProvider.get(this, clazz);
    }
}
