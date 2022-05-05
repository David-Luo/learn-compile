package com.bean.compare.runtime.custom;


import com.bean.compare.runtime.ChangeChecker;
import com.bean.compare.runtime.Differance;
import com.bean.compare.runtime.ListDiffChecker;
import com.bean.compare.runtime.ListDifferance;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 基础的集合校验
 *
 * @author heshan.lwx
 */
public abstract class ListToMapDiffChecker<T> implements ListDiffChecker<T> {

    @Override
    public Optional<Differance> check(List<T> before, List<T> after) {

        if(before == null && after ==null){
            return Optional.empty();
        }else if(before !=null && after == null){
            return Optional.of(new ListDifferance(before,after,Differance.ChangeType.delete));
        }else if (before == null && after != null){
            return Optional.of(new Differance(before,after,Differance.ChangeType.add));
        }else if(before.isEmpty() && after.isEmpty()) {
            return Optional.empty();
        }else if(before.isEmpty() && ! after.isEmpty()){
            return Optional.of(new ListDifferance(before,after,Differance.ChangeType.add));
        }else if(!before.isEmpty() && after.isEmpty()){
            return Optional.of(new ListDifferance(before,after,Differance.ChangeType.delete));
        }

        List<ListDifferance.DifferancePair> differences = new ArrayList<>();
        Map<Object, T> beforeMap = toMap(before);
        Map<Object, T> afterMap = toMap(after);

        ChangeChecker<T> elementChecker = getElementChecker();
        Optional<Differance> diff;
        for (Map.Entry<Object, T> bEle : beforeMap.entrySet()) {
            diff = elementChecker.check(bEle.getValue(), afterMap.get(bEle.getKey()));
            if (diff.isPresent()) {
                differences.add(new ListDifferance.DifferancePair(bEle.getKey(),diff.get()));
            }
        }

        Set<Object> added = new TreeSet<>(afterMap.keySet());
        added.removeAll(beforeMap.keySet());

        for (Object key : added) {
            differences.add(new ListDifferance.DifferancePair(key, new Differance(null, afterMap.get(key),Differance.ChangeType.delete)));
        }
        //TODO 没有判断主键为空的新增场景
        if(differences.isEmpty()){
            return Optional.empty();
        }
        ListDifferance d = new ListDifferance(before, after,Differance.ChangeType.add);
        d.setDifferences(differences);
        return Optional.of(d);
    }

    private T getNotNullValue(List<T> list){
        for (int i = 0; i < list.size(); i++) {
            if(!Objects.isNull(list.get(i))){
                return list.get(i);
            }
        }
        return null;
    }
    protected Map toMap(List<T> collection) {
        return collection.stream().filter(e ->
                        !Objects.isNull(this.keyMapper(e)))
                .collect(Collectors.toMap(this::keyMapper,
                        Function.identity()));
    }

    protected abstract Object keyMapper(T source);

    protected abstract ChangeChecker<T> getElementChecker();
}