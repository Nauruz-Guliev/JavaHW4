package ru.kpfu.itis.gnt.registration.utils.constants;

public class Regex {
    public static final String EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static final String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public static final String DATE = "\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])*";


}
