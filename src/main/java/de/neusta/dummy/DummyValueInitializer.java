package de.neusta.dummy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Component
@SupportedAnnotationTypes("de.nordlb.cavr.DummyValues")
public class DummyValueInitializer extends javax.annotation.processing.AbstractProcessor implements BeanPostProcessor  {

    @Autowired
    ApplicationContext applicationContext;


    @Override
    public Object postProcessBeforeInitialization(Object bean, @NotNull String beanName) throws BeansException {
        System.out.println(bean.getClass().getName());
        inject(bean);
        return bean;
    }

    private void inject(Object bean) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DummyValues.class)) {
                System.out.println(field.getName());
                try {
                    Class<?> clazz = Class.forName(field.getType().getName());
                    field.set(bean, instantiate(clazz, field.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object instantiate(Class<?> type, String name) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (isPrimitiveOrString(type)) {
            return instantiatePrimitive(type, name);
        } else {
            return instantiateComplex(type);
        }
    }


    private Object instantiateComplex(Class<?> type) throws InvocationTargetException, InstantiationException, IllegalAccessException {

        Object instance = null;


        for (Constructor<?> constructor : type.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                instance = constructor.newInstance();
            }
        }

        if (instance == null) {

            Optional<Constructor<?>> first1 = Arrays.stream(type.getConstructors()).findFirst();
            if (first1.isPresent()) {
                Constructor<?> first = first1.get();
                Object[] args = new Object[first.getParameterCount()];

                for (int i = 0; i < first.getParameterCount(); i++) {
                    Class<?> parameterType = first.getParameterTypes()[i];
                    if (isPrimitiveOrString(parameterType)) {
                        args[i] = instantiatePrimitive(parameterType, "param" + i);

                    } else {
                        args[i] = instantiateComplex(parameterType);
                    }
                }
                instance = first.newInstance(args);
            } else {
                System.out.println("Kein Konstruktor?");
            }
        }

        if (null != instance && !isPrimitiveOrString(instance.getClass())) {
            return fillFields(instance);
        }

        return instance;
    }

    private boolean isPrimitiveOrString(Class<?> aClass) {
        if ("String".equals(aClass.getSimpleName())) {
            return true;
        }
        return aClass.isPrimitive();
    }

    private Object fillFields(Object instance) {
        for (Field field : instance.getClass().getDeclaredFields()) {
            System.out.println(field.getName());
            field.setAccessible(true);
            try {
                Class<?> clazz = Class.forName(field.getType().getName());
                field.set(instance, instantiate(clazz, field.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private Object instantiatePrimitive(Class<?> type, String name) {
        String className = type.getSimpleName();

        switch (className) {
            case "String":
                return name;
            case "boolean":
                return false;
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
                return 5;
            case "long":
                return 5L;
            case "float":
                return 5f;
            case "double":
                return 3.0;
            case "char":
                return 'c';
            case "void":
                return void.class;
            default:
                String fqn = className.contains(".") ? className : "java.lang.".concat(className);
                try {
                    return Class.forName(fqn);
                } catch (ClassNotFoundException ex) {
                    throw new IllegalArgumentException("Class not found: " + fqn);
                }
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
