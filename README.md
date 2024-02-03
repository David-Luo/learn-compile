# learn-compile
学习编译原理,代码自动生成

JavaBean基本能力： 

- 复制
- 合并
- 比较
- 转换
- 校验



···java
interface BeanUtil {
    /**
    * 深层拷贝当前对象.
    */
    public static <T> T clone(T from);

    /**
    * 将当前form对象合并到to对象中。
    */
    public  static  <T> T merge(T from, T to);

    /**
    * 将当前对象转换为toType对象.
    * T可以是接口或者抽象类。
    */
    public  static  <T> T map(Object from, Class<T> toType);

    /**
    *  校验当前对象是否符合要求。
    */
    public  static  <T> Option<ValidationResult> validate(Object bean);

    /**
    * 判断object1是否和object2对象有差异。
    */
    public  static  <T> Option<DiffResult> diff(Object object1, Object object1);
}
···