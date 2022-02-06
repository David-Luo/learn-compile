package com.bean.model.element;

import javax.lang.model.element.Name;

public class SimpleName implements Name{
    private CharSequence name;
    public SimpleName(CharSequence name) {
        this.name = name;
    }
    public char charAt(int arg0) {
       return name.charAt(arg0);
    }
  
    public int length() {
        return name.length();
    }
    public boolean contentEquals(CharSequence arg0) {
        return name.toString().contentEquals(arg0);
    }
    public CharSequence subSequence(int arg0, int arg1) {
        return name.subSequence(arg0, arg1);
    }

    @Override
    public boolean equals(Object arg0) {
        if(this == arg0){
            return true;
        }
        if(arg0==null){
            return false;
        }

        return contentEquals(arg0.toString());
    }
    @Override
    public int hashCode() {
        return name.toString().hashCode();
    }
    @Override
    public String toString() {
        return name.toString();
    }
    
}
