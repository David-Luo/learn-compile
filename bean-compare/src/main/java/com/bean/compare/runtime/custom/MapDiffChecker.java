package com.bean.compare.runtime.custom;

import com.bean.compare.runtime.ChangeChecker;
import com.bean.compare.runtime.ChangeCheckerHelper;
import com.bean.compare.runtime.Differance;
import com.bean.compare.runtime.PojoDifferance;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapDiffChecker implements ChangeChecker<Map> {
    @Override
    public Optional<Differance> check(Map left, Map right) {
        if(left == null && right ==null){
            return Optional.empty();
        }else if(left !=null && right == null){
            return Optional.of(new Differance(left,right,Differance.ChangeType.delete));
        }else if (left == null && right != null){
            return Optional.of(new Differance(left,right,Differance.ChangeType.add));
        }else if(left.isEmpty() && right.isEmpty()){
            return Optional.empty();
        }else if(!left.isEmpty() && right.isEmpty()){
            return Optional.of(new Differance(left,right,Differance.ChangeType.delete));
        }else if (left.isEmpty() && !right.isEmpty()){
            return Optional.of(new Differance(left,right,Differance.ChangeType.add));
        }

        Map<String, Differance> entryChange=new HashMap<>();

        Entry entry;Object key;
        Optional<Differance> diff;
        for (Object e : left.entrySet()) {
            entry = (Entry) e;
            key = entry.getKey();
            if(!right.containsKey(key)){
                entryChange.put(key.toString(),new Differance(left,right,Differance.ChangeType.delete));
            }
            diff = ChangeCheckerHelper.diff(entry.getValue(), right.get(key));
            if(diff.isPresent()){
                entryChange.put(key.toString(), diff.get());
            }
        }

        for (Object e: right.entrySet()){
            entry = (Entry) e;
            key = entry.getKey();
            if(left.containsKey(key)){
                continue;
            }
            entryChange.put(key.toString(),new Differance(null,entry.getValue(),Differance.ChangeType.add));
        }

        if(entryChange.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new PojoDifferance(left, right,entryChange));
    }

}
