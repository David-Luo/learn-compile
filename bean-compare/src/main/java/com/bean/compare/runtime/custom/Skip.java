package com.bean.compare.runtime.custom;

import com.bean.compare.runtime.ChangeChecker;
import com.bean.compare.runtime.Differance;

import java.util.Optional;

public class Skip<T>   implements ChangeChecker<T> {
    @Override
    public Optional<Differance> check(T left, T right) {
        return Optional.empty();
    }
}
