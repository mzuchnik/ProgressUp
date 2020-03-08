package com.example.progressup;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context)
    {
        this.openHelper =  new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open()
    {
        this.db = openHelper.getWritableDatabase();
    }

    public void close()
    {
        if(db != null) {
            this.db.close();
        }
    }

    public Cursor rawQuery(String query, String[] args)
    {
       return this.c = db.rawQuery(query,args);
    }

    public String getUserNameById(int id)
    {
        this.c = db.rawQuery("Select name FROM users u WHERE u.id = ?", new String[]{String.valueOf(id)});

        c.moveToFirst();

        int nameId = c.getColumnIndexOrThrow("name");

        return c.getString(nameId);

    }
    public long insert(String table, String nullColumnHack, ContentValues values)
    {
       return this.db.insert(table, nullColumnHack, values);
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs)
    {
        return this.db.update(table, values, whereClause, whereArgs);
    }
    public int delete(String table, String where, String[] args)
    {
        return this.db.delete(table, where, args);
    }

}
