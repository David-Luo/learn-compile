package com.bean.compare.runtime;

import java.util.Optional;

public interface ChangeChecker<T> {
    Optional<Differance> check(T left, T right);

    /**
     * 是否可以检查underCheckClass
    */
    default boolean acceptFor(String underCheckClass){
        return false;
    };
}