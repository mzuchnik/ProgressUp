package com.example.progressup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;

class DatabaseOpenHelper extends SQLiteAssetHelper {


    private static final String DATABASE_NAME="database.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        setForcedUpgrade();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }



}
