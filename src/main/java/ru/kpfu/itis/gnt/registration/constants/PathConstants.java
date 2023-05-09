package ru.kpfu.itis.gnt.registration.constants;

public class PathConstants {

    public static class User {
        public static final String LOGIN = "/user/login";
        public static final String REGISTER = "/user/register";

        public static final String LOGOUT = "/user/logout";
    }

    public static class Error {
        public static final String ACCESS_DENIED = "/error/access_denied";

    }

    public static class Article {
        public static final String CREATE = "/article/create";
        public static final String LIST = "/article/list";
        public static final String SINGLE = "/article/update";
        public static final String DELETE = "/article/delete";


    }

    public static class Category {
        public static final String CREATE = "/category/create";

    }


}
