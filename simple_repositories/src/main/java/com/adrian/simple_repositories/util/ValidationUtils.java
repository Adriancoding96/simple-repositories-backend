package com.adrian.simple_repositories.util;

import java.lang.reflect.Modifier;

public class ValidationUtils {
  public static void validateObject(Object object) {
    if (object.getClass().isPrimitive()) {
        throw new RuntimeException("Provide a non primitive class");
    }

    if (object.getClass().isInterface()) {
        throw new RuntimeException("Provide a non interface class");
    }

    if (object.getClass().isEnum()) {
        throw new RuntimeException("Provide a non enum class");
    }

    if (Modifier.isAbstract(object.getClass().getModifiers())) {
        throw new RuntimeException("Provide a non abstract class");
    }
  }

}
