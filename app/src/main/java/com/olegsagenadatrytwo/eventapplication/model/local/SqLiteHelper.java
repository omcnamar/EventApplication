package com.olegsagenadatrytwo.eventapplication.model.local;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by omcna on 11/21/2017.
 */

public class SqLiteHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "eventsDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_EVENTS = "events";
    public static final String KEY_EVENT_ID = "id";

    public SqLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create table sql
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_EVENTS +
                "(" + KEY_EVENT_ID + " TEXT NOT NULL UNIQUE" + ")";

        //execute sql, create table
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(sqLiteDatabase);
    }
}
