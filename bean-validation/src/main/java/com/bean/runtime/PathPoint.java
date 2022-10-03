package com.bean.runtime;

public abstract class PathPoint implements Point{
    private final Point parent;
    protected String path;

    PathPoint(Point parent){
        this.parent = parent;
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PathPoint other = (PathPoint) obj;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        return true;
    }


    @Override
    public Point getParent() {
        return parent;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public PropertyPoint creatProperty(String propertyName) {
        return new PropertyPointImpl(this, propertyName);
    }
    @Override
    public MapElementPoint creatMapElement(String key) {
        return new MapElementPointImpl(this, key);
    }

    @Override
    public IndexableElementPoint createIndexableElement(int index) {
        return new IndexableElementPointImpl(this, index);
    }
    
    @Override
    public LocatableElementPoint createLocatableElement(ContainerElementPoint.ContainerType type, String name,Object value){
        return new LocatableElementPointImpl(this, type,name,value);
    }

    public static Point createRoot(){
        return new RootPointImpl();
    }

   static class RootPointImpl extends PathPoint implements RootPoint{

        RootPointImpl() {
            super(null);
            this.path = asString();
        }

        @Override
        public String asString() {
            return "#";
        }
        
    }
    class PropertyPointImpl extends PathPoint implements PropertyPoint{
        private String propertyName;
        PropertyPointImpl(Point parent, String propertyName){
            super(parent);
            this.propertyName = propertyName;
            this.path = parent.toString()+asString();
        }
        @Override
        public String getName() {
            return propertyName;
        }
    }

    class IndexableElementPointImpl extends PathPoint implements IndexableElementPoint{
        private int index;
        
        IndexableElementPointImpl(Point parent, int index){
            super(parent);
            this.index = index;
            this.path = parent.toString()+asString();
        }
        @Override
        public int getIndex() {
            return index;
        }
    }

    class MapElementPointImpl extends PathPoint implements MapElementPoint{
        private String key;
        MapElementPointImpl(Point parent, String key){
            super(parent);
            this.key = key;
            this.path = parent.toString()+asString();
        }
        @Override
        public String getKey() {
            return key;
        }
    }
    class LocatableElementPointImpl extends PathPoint implements LocatableElementPoint{
        private ContainerElementPoint.ContainerType type;
        private String name;
        private Object value;
        LocatableElementPointImpl(Point parent,  ContainerElementPoint.ContainerType type, String name,Object value){
            super(parent);
            this.type =type;
            this.name = name;
            this.value = value;
            this.path = parent.getPath()+asString();
        }
        @Override
        public String getName() {
            return name;
        }
        @Override
        public Object getValue() {
            return value;
        }
        @Override
        public ContainerType getContainerType() {
            return type;
        }
    }

    public static void main(String[] args) {
       Point point = PathPoint.createRoot()
            .creatProperty("p")
            .createIndexableElement(10)
            .creatMapElement("k")
            .createLocatableElement(ContainerElementPoint.ContainerType.LIST, "s", null);

        System.out.println(point);
    }
}
