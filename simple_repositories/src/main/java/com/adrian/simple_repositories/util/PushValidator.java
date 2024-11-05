package com.adrian.simple_repositories.util;

import com.adrian.simple_repositories.annotation.ValidPush;
import com.adrian.simple_repositories.model.Push;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PushValidator implements ConstraintValidator<ValidPush, Push> {

  @Override
  public boolean isValid(Push push, ConstraintValidatorContext context) {
    int nonNullCount = 0;

    if(push.getProject() != null) nonNullCount++;
    if(push.getDirectory() != null) nonNullCount++;
    if(push.getFile() != null) nonNullCount++;

    return nonNullCount == 1;
  }
} 
