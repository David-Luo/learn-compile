package com.bean.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

public class ElementUtils {

    public static boolean containsIn(ExecutableElement ele,List<ExecutableElement> elements) {
        for (ExecutableElement executableElement : elements) {
            if (equalExecutableElement(executableElement, ele)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean equalExecutableElement(ExecutableElement a, ExecutableElement b) {
        if (a.getKind() != b.getKind()) {
            return false;
        }
        if (a.getSimpleName() != b.getSimpleName()) {
            return false;
        }
        if (!a.getModifiers().containsAll(b.getModifiers())) {
            return false;
        }
        if (!equalParameters(a.getParameters(), b.getParameters())) {
            return false;
        }
        if (!a.getReturnType().equals(b.getReturnType())) {
            return false;
        }

        return true;
    }

    private static  boolean equalParameters(List<? extends VariableElement> list, List<? extends VariableElement>  list1) {
        return list.stream().map(Object::toString).collect(Collectors.joining())
                .equals(list1.stream().map(Object::toString).collect(Collectors.joining()));
    }
}
