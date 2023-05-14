package by.nika_doroshkevich.security;

import java.util.HashMap;
import java.util.Map;

public enum AppAuthException {
    NOT_FOUND(1, "User not found!", "User not found!"),
    DEFAULT(2, "Invalid username or password", "Invalid username or password.");

    private static final Map<Integer, AppAuthException> BY_code = new HashMap<>();
    private static final Map<String, AppAuthException> BY_SPRING_EXCEPTION = new HashMap<>();

    static {
        for (AppAuthException e : values()) {
            BY_code.put(e.code, e);
            BY_SPRING_EXCEPTION.put(e.springException, e);
        }
    }

    private final int code;
    private final String springException;
    private final String appException;

    AppAuthException(int code, String springException, String appException) {
        this.code = code;
        this.springException = springException;
        this.appException = appException;
    }

    public int getCode() {
        return code;
    }

    public String getSpringException() {
        return springException;
    }

    public String getAppException() {
        return appException;
    }

    public static AppAuthException valueOfCode(Integer code) {
        AppAuthException authException = BY_code.get(code);
        if (authException == null) {
            return AppAuthException.DEFAULT;
        }
        return authException;
    }

    public static AppAuthException valueOfCode(String code) {
        return valueOfCode(Integer.parseInt(code));
    }

    public static AppAuthException valueOfSpringException(String springException) {
        AppAuthException authException = BY_SPRING_EXCEPTION.get(springException);
        if (authException == null) {
            return AppAuthException.DEFAULT;
        }
        return authException;
    }
}
