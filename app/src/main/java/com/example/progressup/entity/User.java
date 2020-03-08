package com.example.progressup.entity;

import android.provider.BaseColumns;

public class User implements BaseColumns {

    private User(){}

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_ID_PLAN_MONITOR = "idPlanMonitor";
    public static final String COLUMN_ID_PLAN_AIM = "idPlanAim";
    public static final String COLUMN_ROLE = "role";

}
