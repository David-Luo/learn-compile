package com.bean.compare.runtime;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

import com.bean.compare.meta.ChangeChekerRegistry;

public class ChangeCheckerHelper {
    private static ChangeCheckerFactory changeCheckerFactory = new ChangeCheckerFactory();
    private static ChangeChekerRegistry changeChekerRegistry = new ChangeChekerRegistry();
    /**
     * 比较对象前后变化
     * @param left
     * @param right
     * @return
     */
    public static Optional<Differance> diff(Object left, Object right){
        ChangeChecker checker = ChangeCheckerHelper.getChangeChecker(left, right);
        return checker.check(left, right);
    }

    public static ChangeChecker getChangeChecker(Object left, Object right) {
        if (Objects.isNull(left) || Objects.isNull(right)) {
            return changeCheckerFactory.getDefault();
        }
        if (!isSameClass(left, right)) {
            throw new RuntimeException(String.format("can`t compare between %s and %s", left.getClass().toString(), right.getClass().toString()));
        }
        ;
        Class<? extends ChangeChecker> clazz =changeChekerRegistry.lookup(left.getClass());
        return changeCheckerFactory.getInstance(clazz);
    }

    public static boolean isSameClass(Object left, Object right){
        if(Objects.isNull(left) || Objects.isNull(right)){
            return true;
        }
        return left.getClass() == right.getClass();
    }

    public static <T> Class<T> getTClass(Class<T> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        if(genericInterfaces.length==0) {
            return (Class<T>) Object.class;
        }
        Type genericSuperclass = genericInterfaces[0];
        if (genericSuperclass instanceof ParameterizedType) {
            Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            if (type instanceof Class)
                return (Class<T>) type;
        }
        return (Class<T>) Object.class;
    }
}

