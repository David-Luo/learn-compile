package com.bean.compare.runtime.custom;

import com.bean.compare.runtime.ChangeChecker;
import com.bean.compare.runtime.Differance;

import java.util.Optional;

public class ComparableDiffChecker<T extends Comparable>  implements ChangeChecker<T> {
    @Override
    public Optional<Differance> check(T before, T after) {
        if(before == null && after ==null){
            return Optional.empty();
        }else if(before !=null && after == null){
            return Optional.of(new Differance(before,after,Differance.ChangeType.delete));
        }else if (before == null && after != null){
            return Optional.of(new Differance(before,after,Differance.ChangeType.add));
        }else if(before.compareTo(after)==0){
            return Optional.empty();
        }
        return Optional.of(new Differance(before,after,Differance.ChangeType.edit));
    }
}
