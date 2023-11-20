package by.clevertec.lobacevich.validator;

import by.clevertec.lobacevich.data.UserDto;
import by.clevertec.lobacevich.exception.ValidationException;
import org.aspectj.lang.annotation.Aspect;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
public class Validator {

    public static final Validator INSTANCE = new Validator();
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Validator() {
    }

    public static Validator getINSTANCE() {
        return INSTANCE;
    }

    public static void validateToCreate(UserDto userDto) {
        if (userDto.getId() != null) {
            throw new ValidationException("Validation error: id must be null");
        }
    }

    public static void validateToUpdate(UserDto userDto) {
        if (userDto.getId() == null) {
            throw new ValidationException("Validation error: id must be null");
        }
        validateFields(userDto);
    }

    private static void validateFields(UserDto userDto) {
        String firstName = userDto.getFirstname();
        if (firstName == null || firstName.length() < 2) {
            throw new ValidationException("Validation error: incorrect first name");
        }
        String lastName = userDto.getLastname();
        if (lastName == null || lastName.length() < 2) {
            throw new ValidationException("Validation error: field id must be filled");
        }
        int yearOfBirth = userDto.getDateOfBirth().getYear();
        int yearNow = LocalDate.now().getYear();
        if (userDto.getDateOfBirth() == null || yearNow - yearOfBirth > 130 ||
                yearOfBirth > yearNow) {
            throw new ValidationException("Validation error: incorrect date of birth");
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(userDto.getEmail());
        if (!matcher.matches()) {
            throw new ValidationException("Validation error: incorrect e-mail");
        }
    }
}
