package com.bean.compare.runtime.custom;

import com.bean.compare.runtime.ChangeChecker;
import com.bean.compare.runtime.Differance;

import java.util.Objects;
import java.util.Optional;

public class AtomicDiffChecker<T>  implements ChangeChecker<T> {
    @Override
    public Optional<Differance> check(T before, T after) {
        if(before == null && after ==null){
            return Optional.empty();
        }else if(before !=null && after == null){
            return Optional.of(new Differance(before,after,Differance.ChangeType.delete));
        }else if (before == null && after != null){
            return Optional.of(new Differance(before,after,Differance.ChangeType.add));
        }else if(Objects.equals(before,after)){
            return Optional.empty();
        }
        return Optional.of(new Differance(before,after,Differance.ChangeType.edit));
    }
}
