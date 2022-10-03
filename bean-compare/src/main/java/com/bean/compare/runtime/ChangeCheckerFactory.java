package com.bean.compare.runtime;

public class ChangeCheckerFactory {
    public <T extends ChangeChecker> T getInstance(Class<T> clazz) {
        return null;
    }

    public <T extends ChangeChecker> T getDefault() {
        return null;
    }

}
