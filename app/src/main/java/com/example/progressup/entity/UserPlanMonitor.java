package com.example.progressup.entity;

import android.provider.BaseColumns;

public class UserPlanMonitor implements BaseColumns {

    private UserPlanMonitor(){}

    public static final String TABLE_NAME = "userPlanMonitor";
    public static final String COLUMN_ID = "idUserPlanMonitor";
    public static final String COLUMN_ID_USER = "idUser";
    public static final String COLUMN_ID_PLAN_MONITOR = "idPlanMonitor";
    public static final String COLUMN_CREATE_DATE = "createDate";

}
