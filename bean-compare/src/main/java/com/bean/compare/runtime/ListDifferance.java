package com.bean.compare.runtime;

import java.util.List;

public class ListDifferance extends Differance{
    private List<DifferancePair> differences;
    public ListDifferance() {
    }

    public ListDifferance(Object before, Object after, ChangeType changeType) {
        super(before, after,changeType);
    }

    public List<DifferancePair> getDifferences() {
        return differences;
    }

    public void setDifferences(List<DifferancePair> differences) {
        this.differences = differences;
    }

    public static class DifferancePair {
        private Object key;
        private Object value;
        public DifferancePair(Object key, Object value){
            this.key = key;
            this.value = value;
        }

        public Object getKey() {
            return key;
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
