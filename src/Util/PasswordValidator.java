package Util;

public class PasswordValidator {
    public Boolean validate(String password) {
        return password.length() >= 6;
    }
}
