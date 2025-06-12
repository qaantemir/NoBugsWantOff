package java_express.complex_exercises.user_data_validator;

public class UserValidator<T extends User> {
    private static boolean validationEnabled = false;

    public static void setValidation(boolean isValidationEnabled) {
        UserValidator.validationEnabled = isValidationEnabled;
    }

    public boolean validateUserData(T user) throws InvalidUserException {
        if (!validationEnabled) return true;
        String name = user.getName();
        Integer age = user.getAge();
        String email = user.getEmail();

        if (!(this.checkName(name) && this.checkAge( age) && this.checkEmail(email)))
            throw new InvalidUserException();

        return true;
    }

    private boolean checkName(String name) {
        return name != null && !(name.isBlank()) && Character.isUpperCase(name.charAt(0));
    }

    private boolean checkAge(Integer age) {
        return age != null && (age >= 18 && age <= 100);
    }

    private boolean checkEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }
}
