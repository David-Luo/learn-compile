package com.bean.compare.runtime.custom;

import com.bean.compare.runtime.ChangeChecker;
import com.bean.compare.runtime.ListDiffChecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrictListDiffChecker<T extends List> extends ListToMapDiffChecker<T> implements ListDiffChecker<T> {

    @Override
    protected Map toMap(List<T> list) {
        Map map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            map.put(i,list.get(i));
        }
        return map;
    }

    @Override
    protected Object keyMapper(T source) {
        return null;
    }

    @Override
    protected ChangeChecker<T> getElementChecker() {
        return null;
    }

}
