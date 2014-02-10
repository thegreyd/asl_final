package com.sid.asl_take3;

/**
 * Created by Siddharth on 2/9/14.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database_Helper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "timetable.db";
    private static final String TABLE_CS1 = "cs1";
    private static final String TABLE_CS2 = "cs2";
    private static final String TABLE_CS3 = "cs3";
    private static final int DATABASE_VERSION = 3;
    private String table;

    public Database_Helper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getTimetableByDay(long class_index, String day) {

        if(class_index==0)
            table=TABLE_CS1;
        else if(class_index==1)
            table=TABLE_CS2;
        else if(class_index==2)
            table=TABLE_CS3;

        SQLiteDatabase db = getReadableDatabase();

        assert db != null;
        Cursor c = db.rawQuery("select * from "+table+" where Day='"+day+"'",null);

        c.moveToFirst();
        return c;

    }

}