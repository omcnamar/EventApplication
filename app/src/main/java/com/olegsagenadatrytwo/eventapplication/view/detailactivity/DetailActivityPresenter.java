package com.olegsagenadatrytwo.eventapplication.view.detailactivity;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.olegsagenadatrytwo.eventapplication.model.local.SqLiteHelper;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omcna on 11/20/2017.
 */

public class DetailActivityPresenter implements DetailActivityContract.Presenter {

    public static final String TAG = "DetailPresenter";
    private static final String PASSWORD_DB = "Password";
    private DetailActivityContract.View view;

    @Override
    public void attachView(DetailActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void removeView() {
        this.view = view;
    }

    @Override
    public void saveEvent(String eventId, Context context) {
        SQLiteDatabase.loadLibs(context.getApplicationContext());
        SqLiteHelper usersDatabaseHelper = new SqLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = usersDatabaseHelper.getWritableDatabase(PASSWORD_DB);

        sqLiteDatabase.insert(SqLiteHelper.TABLE_EVENTS, null, getContentValues(eventId));
    }

    @Override
    public void removeEvent(String eventId, Context context) {
        SQLiteDatabase.loadLibs(context.getApplicationContext());
        SqLiteHelper usersDatabaseHelper = new SqLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = usersDatabaseHelper.getWritableDatabase(PASSWORD_DB);

        sqLiteDatabase.delete(SqLiteHelper.TABLE_EVENTS, SqLiteHelper.KEY_EVENT_ID + "=" + eventId, null);
    }

    @Override
    public List<String> getSavedEvents(Context context) {
        Log.d(TAG, "getSavedEvents: "+ "Called");
        SQLiteDatabase.loadLibs(context.getApplicationContext());
        SqLiteHelper usersDatabaseHelper = new SqLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = usersDatabaseHelper.getWritableDatabase(PASSWORD_DB);
        Cursor cursor = sqLiteDatabase.query(SqLiteHelper.TABLE_EVENTS, null, null, null, null, null, null);

        List<String> listOfSavedIds = new ArrayList<>();

        boolean isItem = cursor.moveToFirst();
        if(isItem) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(SqLiteHelper.KEY_EVENT_ID));
                listOfSavedIds.add(id);
                Log.d(TAG, "getSavedEvents: " + id);

            } while (cursor.moveToNext());
        }
        cursor.close();


        return listOfSavedIds;
    }

    @NonNull
    private ContentValues getContentValues(String eventId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqLiteHelper.KEY_EVENT_ID, eventId);
        return contentValues;
    }
}
