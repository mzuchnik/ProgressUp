package com.example.progressup.entity;

import android.provider.BaseColumns;

public class PlanMonitor implements BaseColumns {

    private PlanMonitor(){}

    public static final String TABLE_NAME = "planMonitor";
    public static final String COLUMN_ID = "idPlanMonitor";
    public static final String COLUMN_CHEST = "chest";
    public static final String COLUMN_STOMACH = "stomach";
    public static final String COLUMN_ARM = "arm";
    public static final String COLUMN_FOREARM = "forearm";
    public static final String COLUMN_HIPS = "hips";
    public static final String COLUMN_SUCCEED = "succeed";
    public static final String COLUMN_CALF = "calf";
    public static final String COLUMN_PHOTO_URI = "photoURI";
}
