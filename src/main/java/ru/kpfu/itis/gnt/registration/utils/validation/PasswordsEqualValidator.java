package ru.kpfu.itis.gnt.registration.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kpfu.itis.gnt.registration.dto.UserDto;

public class PasswordsEqualValidator implements ConstraintValidator<PasswordsEqual, UserDto> {

    @Override
    public void initialize(PasswordsEqual constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        return userDto.getPassword().equals(userDto.getPasswordRepeat());
    }
}
