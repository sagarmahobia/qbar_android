package com.sagar.qbar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sagar.qbar.database.models.ResultType;
import com.sagar.qbar.database.models.ResultWrapper;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18. at 22:32
 */

public class HistoryDbHelper extends SQLiteOpenHelper {

    /*

    table name = results

    column _id for unique id
    column result_data for resultData
    column timestamp for timestamp
    column result_type for result time

    */


    private static final String DATABASE_NAME = "history_db";

    private static int DATABASE_VERSION = 1;

    public static final String ID = "_id";
    public static final String DATA = "result_data";
    private static final String TIMESTAMPS = "timestamp";
    public static final String RESULT_TYPE = "result_type";

    private static final String TABLE_NAME = "results";


    private static final String CREATE_RESULTS_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS results ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "result_data TEXT NOT NULL, " +
                    "result_type INTEGER NOT NULL, " +
                    "timestamp INTEGER NOT NULL );";

    private static final String SQL_FOR_GETTING_ALL_RESULTS = "select * from results order by timestamp desc";

    private static final String SQL_FOR_GETTING_RESULT = "select * from  results where _id = ?";

    private static final String SQL_FOR_DELETE_RESULT_ROW = "delete from results where _id = ?";

    private static final String SQL_FOR_DELETING_ALL = "delete from results where 1";


    public HistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESULTS_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing to do..
    }

    public void storeResult(ResultWrapper resultWrapper) {
        ContentValues values = new ContentValues();

        values.put(DATA, resultWrapper.getText());
        values.put(TIMESTAMPS, resultWrapper.getTimestamp());

        values.put(RESULT_TYPE, resultWrapper.getResultType().getId());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getHistoriesCursor() {
        return getReadableDatabase().rawQuery(SQL_FOR_GETTING_ALL_RESULTS, null);

    }

    public void deleteHistory(int id) {
        SQLiteDatabase database = getWritableDatabase();

        database.execSQL(SQL_FOR_DELETE_RESULT_ROW.replace("?", String.valueOf(id)));

        database.close();

    }

    public ResultWrapper getResultWrapper(long id) {

        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(SQL_FOR_GETTING_RESULT.replace("?", String.valueOf(id)), null);

        cursor.moveToFirst();

        int resultTypeId = cursor.getInt(cursor.getColumnIndex(RESULT_TYPE));
        String text = cursor.getString(cursor.getColumnIndex(DATA));
        long timestamp = cursor.getLong(cursor.getColumnIndex(TIMESTAMPS));


        cursor.close();
        database.close();


        ResultType resultType = ResultType.getResultTypeFromId(resultTypeId);

        return new ResultWrapper(resultType, text, timestamp);

    }

    public void deleteAll() {
        SQLiteDatabase database = getWritableDatabase();

        database.execSQL(SQL_FOR_DELETING_ALL);

        database.close();

    }
}
