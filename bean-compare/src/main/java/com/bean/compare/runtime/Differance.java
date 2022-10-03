package com.bean.compare.runtime;


public class Differance {
    private Object before;
    private Object after;
    private ChangeType changeType;

    public Differance() {
    }
    public Differance(Object before, Object after, ChangeType changeType) {
        super();
        this.before = before;
        this.after = after;
        this.changeType = changeType;
    }

    public enum ChangeType{
        add, delete,edit,attributeChange,addElement,deleteElement,elementChange
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
        this.before = before;
    }

    public Object getAfter() {
        return after;
    }

    public void setAfter(Object after) {
        this.after = after;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }
    // @Override
    // public String toString() {
    //     String s="Differance";
    //     switch (changeType){
    //         case add -> s = "'"+changeType+ " value " + after+"'";
    //         case delete -> s = "'"+changeType+ " value " + before+"'";
    //         default -> s= "'"+changeType+" from " + before + " to " + after+"'";
    //     }

    //     return s;
    // }
}