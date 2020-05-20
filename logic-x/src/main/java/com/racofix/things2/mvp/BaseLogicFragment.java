package com.racofix.things2.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseLogicFragment extends Fragment {

    private LogicProvider logicProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logicProvider = LogicProvider.getInstance();
        logicProvider.put(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logicProvider.remove(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseLogic> T getLogic(Class<T> clazz) {
        return logicProvider.get(this, clazz);
    }
}
