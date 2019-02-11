package ioc.lifecirycle;

import annotation.Injection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationInjection {

    public Object inject(Object obj) {

        Field fields[] = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Injection injectionAnnotation = field.getAnnotation(Injection.class);
            if (injectionAnnotation != null) {
                try {
                    obj.getClass()
                            .getMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1),
                                    new Class<?>[]{String.class})
                            .invoke(obj, injectionAnnotation.value());
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        Method methods[] = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Injection injectionAnnotation = method.getAnnotation(Injection.class);
            if (injectionAnnotation != null) {
                try {
                    method.invoke(obj, injectionAnnotation.value());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
}
