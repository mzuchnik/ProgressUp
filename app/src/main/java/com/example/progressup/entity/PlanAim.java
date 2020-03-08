package com.example.progressup.entity;

import android.provider.BaseColumns;

public class PlanAim implements BaseColumns {

    private PlanAim(){}

    public static final String TABLE_NAME = "planAim";
    public static final String COLUMN_ID = "idPlanAim";
    public static final String COLUMN_AIM = "aim";
    public static final String COLUMN_ACTIVITY = "activity";
    public static final String COLUMN_WEIGHT_CURRENT = "weightCurrent";
    public static final String COLUMN_WEIGHT_AIM = "weightAim";
    public static final String COLUMN_DATE_AIM = "dateAim";
    public static final String COLUMN_REMIND = "remind";

}
