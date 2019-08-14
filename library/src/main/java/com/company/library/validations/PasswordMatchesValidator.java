package com.company.library.validations;

import com.company.library.DTO.Registration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        Registration userObject = (Registration) obj;
        return userObject.getPassword().equals(userObject.getMatchingPassword());
    }
}
