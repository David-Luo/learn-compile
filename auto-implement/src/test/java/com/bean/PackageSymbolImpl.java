package com.bean;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;

public class PackageSymbolImpl implements PackageSymbol {
  private ElementKind kind;

  private Set<Modifier> modifiers;

  private List<? extends AnnotationMirror> annotationMirrors;

  private Name simpleName;

  private List<? extends Element> enclosedElements;

  private Element enclosingElement;

  private Name qualifiedName;

  @Override
  public ElementKind getKind() {
    return kind;
  }

  @Override
  public Set<Modifier> getModifiers() {
    return modifiers;
  }

  @Override
  public List<? extends AnnotationMirror> getAnnotationMirrors() {
    return annotationMirrors;
  }

  @Override
  public Name getSimpleName() {
    return simpleName;
  }

  @Override
  public List<? extends Element> getEnclosedElements() {
    return enclosedElements;
  }

  @Override
  public Element getEnclosingElement() {
    return enclosingElement;
  }

  @Override
  public Name getQualifiedName() {
    return qualifiedName;
  }

  public static Builder builder() {
    return new PackageSymbolImpl.Builder();
  }

  @Override
  public int hashCode() {
    int result = 17;
    if (kind != null) {result = 31 * result + kind.hashCode();}
    if (modifiers != null) {result = 31 * result + modifiers.hashCode();}
    if (annotationMirrors != null) {result = 31 * result + annotationMirrors.hashCode();}
    if (simpleName != null) {result = 31 * result + simpleName.hashCode();}
    if (enclosedElements != null) {result = 31 * result + enclosedElements.hashCode();}
    if (enclosingElement != null) {result = 31 * result + enclosingElement.hashCode();}
    if (qualifiedName != null) {result = 31 * result + qualifiedName.hashCode();}
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj){return true;}
    if(!(obj instanceof PackageSymbolImpl))return false;
    PackageSymbolImpl that = (PackageSymbolImpl)obj;
    if(!Objects.equals(kind, that.kind))return false;
    if(!Objects.equals(modifiers, that.modifiers))return false;
    if(!Objects.equals(annotationMirrors, that.annotationMirrors))return false;
    if(!Objects.equals(simpleName, that.simpleName))return false;
    if(!Objects.equals(enclosedElements, that.enclosedElements))return false;
    if(!Objects.equals(enclosingElement, that.enclosingElement))return false;
    if(!Objects.equals(qualifiedName, that.qualifiedName))return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(String.format("{\"class\":\"%s\"", "PackageSymbolImpl"));
    if(kind != null){
    sb.append(String.format(",\"kind\":\"%s\"", kind));
    }
    if(modifiers != null){
    sb.append(String.format(",\"modifiers\":\"%s\"", modifiers));
    }
    if(annotationMirrors != null){
      sb.append(",\"annotationMirrors\":[");
      for (Object it : annotationMirrors) {
        if (it !=null) {
          sb.append(it.toString()).append(',');
        }
      }
      if(sb.charAt(sb.length()-1)==','){
        sb.deleteCharAt(sb.length()-1);
      }
      sb.append(']');
    }
    if(simpleName != null){
    sb.append(String.format(",\"simpleName\":\"%s\"", simpleName));
    }
    if(enclosedElements != null){
    sb.append(String.format(",\"enclosedElements\":\"%s\"", enclosedElements));
    }
    if(enclosingElement != null){
    sb.append(String.format(",\"enclosingElement\":\"%s\"", enclosingElement));
    }
    if(qualifiedName != null){
    sb.append(String.format(",\"qualifiedName\":\"%s\"", qualifiedName));
    }
    sb.append('}');
    return sb.toString();
  }

  public static class Builder {
    private PackageSymbolImpl target;

    Builder() {
      this.target = new PackageSymbolImpl();
    }

    public PackageSymbolImpl build() {
      return this.target;
    }

    public Builder kind(ElementKind kind) {
      target.kind=kind;
       return this;
    }

    public Builder modifiers(Set<Modifier> modifiers) {
      target.modifiers=modifiers;
       return this;
    }

    public Builder annotationMirrors(List<? extends AnnotationMirror> annotationMirrors) {
      target.annotationMirrors=annotationMirrors;
       return this;
    }

    public Builder simpleName(Name simpleName) {
      target.simpleName=simpleName;
       return this;
    }

    public Builder enclosedElements(List<? extends Element> enclosedElements) {
      target.enclosedElements=enclosedElements;
       return this;
    }

    public Builder enclosingElement(Element enclosingElement) {
      target.enclosingElement=enclosingElement;
       return this;
    }

    public Builder qualifiedName(Name qualifiedName) {
      target.qualifiedName=qualifiedName;
       return this;
    }
  }

  public static void main(String[] args) {
    try {
     Class<?> clazz = Class.forName("java.lang.Iterable");
     System.out.println(clazz.isAssignableFrom(Collection.class));
     System.out.println(Iterable.class.isAssignableFrom(clazz));
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
