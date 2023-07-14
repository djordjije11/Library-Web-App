package com.djordjije11.libraryappapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BeforeValidator.class)
public @interface Before {
    public int years() default 0;
    public int months() default 0;
    public int days() default 0;

    public String message() default "Invalid date.";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
