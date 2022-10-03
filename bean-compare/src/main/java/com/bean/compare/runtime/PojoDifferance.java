package com.bean.compare.runtime;

import java.util.HashMap;
import java.util.Map;

public class PojoDifferance extends Differance {
    private Map<String, Differance> attributeChange;

    public PojoDifferance() {
    }
    public PojoDifferance(Object before, Object after, ChangeType changeType) {
        super(before, after,changeType);
        this.attributeChange = new HashMap<>();
    }

    public PojoDifferance(Object before, Object after, Map<String, Differance> attributeChange) {
        super(before, after,ChangeType.attributeChange);
        this.attributeChange = attributeChange;
    }

    public Map<String, Differance> getAttributeChange() {
        return attributeChange;
    }

    public void setAttributeChange(Map<String, Differance> attributeChange) {
        this.attributeChange = attributeChange;
    }

    @Override
    public String toString() {
        return "Pojo " + getChangeType() + "=" + attributeChange + "]";
    }

}