package com.free.agent;

/**
 * Created by antonPC on 18.08.15.
 */
public final class FreeAgentAPI {
    public static final String OK = "OK";
    public static final int VALIDATION_ERROR = 460;
    public static final int LOGIN_ERROR = 461;
    public static final int UNEXPECTED_ERROR = 499;

    public static final String INFO_ABOUT_USER = "/user/info";
    public static final String GET_USER_BY_ID = "/user/{id}";
    public static final String SAVE_IMAGE = "/user/setImage";
    public static final String GET_IMAGE = "/user/getImage";
    public static final String GET_ALL_SPORTS = "/sport";
    public static final String IS_AUTHENTICATION = "/isAuthentication";
}