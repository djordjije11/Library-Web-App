package com.djordjije11.libraryappapi.validation.before;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BeforeLocalDateValidator implements ConstraintValidator<Before, LocalDate> {
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
        return localDate == null || localDate.isBefore(LocalDate.now().minusYears(years).minusMonths(months).minusDays(days));
    }
}
