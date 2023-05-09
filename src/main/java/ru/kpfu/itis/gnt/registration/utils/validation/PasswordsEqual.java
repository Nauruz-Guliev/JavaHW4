package ru.kpfu.itis.gnt.registration.utils.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PasswordsEqualValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordsEqual {

    String message() default "Passwords are not equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
