package com.adrian.simple_repositories.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.adrian.simple_repositories.util.PushValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Constraint(validatedBy = PushValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPush {

  String message() default "A Push can only contain one of Project, Folder or File";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
