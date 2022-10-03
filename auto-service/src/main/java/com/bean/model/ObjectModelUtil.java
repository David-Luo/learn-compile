package com.bean.model;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

public class ObjectModelUtil {
    private List<ExecutableElement> methods;
    private Elements elementUtils;

    public ObjectModelUtil(Elements elements) {
        this.elementUtils = elements;

        methods = ElementFilter.methodsIn(
                elements.getAllMembers(
                        elements.getTypeElement(
                                Object.class.getCanonicalName())));

    }

    public boolean isJavaLangObjectMethod(ExecutableElement method) {
        for (ExecutableElement executableElement : methods) {
            if (ElementUtils.equalExecutableElement(executableElement, method)) {
                return true;
            }
        }

        return false;
    }
}
