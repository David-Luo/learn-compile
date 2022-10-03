package com.bean.processor;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Flow.Processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;
import javax.lang.model.SourceVersion;

import com.bean.annotation.AutoImplementService;
import com.bean.generator.ServiceGenerator;
import com.bean.generator.ServiceGeneratorRegistry;
import com.google.auto.common.MoreTypes;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;

import static com.google.auto.common.AnnotationMirrors.getAnnotationValue;
import static com.google.auto.common.MoreElements.getAnnotationMirror;
import static com.google.auto.common.MoreStreams.toImmutableSet;


@SupportedAnnotationTypes({ "com.bean.annotation.AutoImplementService" })
@SupportedSourceVersion(SourceVersion.RELEASE_14)
@AutoService(Processor.class)
public class AutoImplementServiceProcessor extends AbstractProcessor {
    
    private ServiceGeneratorRegistry generatorRegistry = new ServiceGeneratorRegistry();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // Itearate over all @AutoImplementService annotated elements
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(AutoImplementService.class)) {
            // Check if a class has been annotated with @AutoImplementService
            if (annotatedElement.getKind() != ElementKind.INTERFACE) {
                return false;
            }
            TypeElement typeElement = (TypeElement) annotatedElement;
            Class<? extends ServiceGenerator> generatorClass = getDeclared(typeElement);
            if (generatorClass == null) {
                generatorClass = lookup(typeElement);
            }
            if (generatorClass == null) {
                throw new RuntimeException("No ServiceGenerator found for " + typeElement);
            }
            
            try {
                handle(generatorClass, typeElement);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Class<? extends ServiceGenerator> lookup(TypeElement typeElement) {
        List<? extends TypeMirror> interfaceDeclare = typeElement.getInterfaces();
        Class<? extends ServiceGenerator> generator;
        for (TypeMirror type : interfaceDeclare) {
            TypeElement providerType = MoreTypes.asTypeElement(type);
            generator = generatorRegistry.lookup(providerType.getQualifiedName().toString());
            if (generator != null) {
                return generator;
            }
        }
        return null;
    }

    private Class<? extends ServiceGenerator> getDeclared(TypeElement typeElement){
        AnnotationMirror annotationMirror = getAnnotationMirror(typeElement, AutoImplementService.class).get();
        Set<DeclaredType> providerInterfaces = getValueFieldOfClasses(annotationMirror);
        if (providerInterfaces.isEmpty()) {
            //TODO
          }
          Class<? extends ServiceGenerator> generatorClass = null;
          for (DeclaredType providerInterface : providerInterfaces) {
            TypeElement providerType = MoreTypes.asTypeElement(providerInterface);
            if (ServiceGenerator.DefaultGenerator.class.getName().equals(providerType.getQualifiedName().toString())) {
                continue;
            }
            // generatorClass = generatorRegistry.lookup(providerType.getQualifiedName().toString());
            boolean isGeneratorClass = MoreTypes.isTypeOf(ServiceGenerator.class, providerType.asType());
            if (isGeneratorClass) {
                try {
                    return (Class<? extends ServiceGenerator>) Class.forName(providerType.getQualifiedName().toString());
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
          }
          
        
        return null;
    }

  /**
   * Returns the contents of a {@code Class[]}-typed "value" field in a given {@code
   * annotationMirror}.
   */
  private ImmutableSet<DeclaredType> getValueFieldOfClasses(AnnotationMirror annotationMirror) {
    return getAnnotationValue(annotationMirror, "value")
        .accept(
            new SimpleAnnotationValueVisitor8<ImmutableSet<DeclaredType>, Void>(ImmutableSet.of()) {
              @Override
              public ImmutableSet<DeclaredType> visitType(TypeMirror typeMirror, Void v) {
                // TODO(ronshapiro): class literals may not always be declared types, i.e.
                // int.class, int[].class
                return ImmutableSet.of(MoreTypes.asDeclared(typeMirror));
              }

              @Override
              public ImmutableSet<DeclaredType> visitArray(
                  List<? extends AnnotationValue> values, Void v) {
                return values.stream()
                    .flatMap(value -> value.accept(this, null).stream())
                    .collect(toImmutableSet());
              }
            },
            null);
  }
    private boolean handle(Class<? extends ServiceGenerator> generatorClass, TypeElement element)
            throws InstantiationException, IllegalAccessException {
        ServiceGenerator generator = generatorClass.newInstance();
        generator.init(processingEnv);
        generator.processing((TypeElement) element);
        return false;
    }
}
