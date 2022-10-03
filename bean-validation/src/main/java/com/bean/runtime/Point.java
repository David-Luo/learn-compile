package com.bean.runtime;

public interface Point {
    
    Point getParent();

    String getPath();

    String asString();
    // PathPoint createRoot();

    PropertyPoint creatProperty(String propertyName);
    MapElementPoint creatMapElement(String key);
    IndexableElementPoint createIndexableElement(int index);
    LocatableElementPoint createLocatableElement(ContainerElementPoint.ContainerType type, String name,Object value);

    public interface RootPoint extends Point{

    }
    public interface PropertyPoint extends Point{
        String getName();

        default String asString(){
            return '.'+getName();
        }
    }
    public interface ContainerElementPoint extends Point{
        ContainerType getContainerType();

        enum ContainerType{
            ANY,LIST,SET,MAP
        }
    }
    public interface MapElementPoint extends ContainerElementPoint{
        String getKey();
        @Override
        default ContainerType getContainerType() {
            return ContainerType.MAP;
        }
        default String asString(){
            return "['"+getKey()+"']";
        }
    }
    public interface IndexableElementPoint extends ContainerElementPoint{
        int getIndex();
        @Override
        default ContainerType getContainerType() {
            return ContainerType.LIST;
        }
        default String asString(){
            return ""+'['+getIndex()+']';
        }
    }
    public interface LocatableElementPoint extends ContainerElementPoint{
        String getName();
        Object getValue();
        @Override
        default ContainerType getContainerType() {
            return ContainerType.SET;
        }
        @Override
        default String asString(){
            String value = getValue() == null?"null":"'"+getValue()+"'";
            return "["+getName()+"=="+value+"]";
        }
    }
}
