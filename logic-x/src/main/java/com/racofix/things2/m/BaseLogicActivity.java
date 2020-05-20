package com.racofix.things2.m;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
