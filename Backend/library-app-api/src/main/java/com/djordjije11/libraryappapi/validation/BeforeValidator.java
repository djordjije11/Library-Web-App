package com.djordjije11.libraryappapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BeforeValidator implements ConstraintValidator<Before, LocalDate> {
    private int years;
    private int months;
    private int days;

    @Override
    public void initialize(Before constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        years = constraintAnnotation.years();
        months = constraintAnnotation.months();
        days = constraintAnnotation.days();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if(localDate == null){
            return true;
        }
        return localDate.isBefore(LocalDate.now().minusYears(years).minusMonths(months).minusDays(days));
    }
}
