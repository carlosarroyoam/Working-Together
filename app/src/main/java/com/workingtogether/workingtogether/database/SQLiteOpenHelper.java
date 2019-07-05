package com.workingtogether.workingtogether.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String SQL_CREATE_ACTIVITIES_TABLE =
            "CREATE TABLE " + DatabaseSchema.ActivitiesTable.TABLE_NAME + " (" +
                    DatabaseSchema.ActivitiesTable.Cols.UUID + " INTEGER PRIMARY KEY," +
                    DatabaseSchema.ActivitiesTable.Cols.TITLE + " TEXT," +
                    DatabaseSchema.ActivitiesTable.Cols.DESCRIPTION + " TEXT," +
                    DatabaseSchema.ActivitiesTable.Cols.CREATED_AT + " TEXT," +
                    DatabaseSchema.ActivitiesTable.Cols.UPDATED_AT + " TEXT," +
                    DatabaseSchema.ActivitiesTable.Cols.DELIVERY_DATE + " TEXT)";

    private static final String SQL_DELETE_ACTIVITIES_TABLE =
            "DROP TABLE IF EXISTS " + DatabaseSchema.ActivitiesTable.TABLE_NAME;

    private static final String SQL_CREATE_HOMEWORKS_TABLE =
            "CREATE TABLE " + DatabaseSchema.HomeworksTable.TABLE_NAME + " (" +
                    DatabaseSchema.HomeworksTable.Cols.UUID + " INTEGER PRIMARY KEY," +
                    DatabaseSchema.HomeworksTable.Cols.TITLE + " TEXT," +
                    DatabaseSchema.HomeworksTable.Cols.DESCRIPTION + " TEXT," +
                    DatabaseSchema.HomeworksTable.Cols.CREATED_AT + " TEXT," +
                    DatabaseSchema.HomeworksTable.Cols.UPDATED_AT + " TEXT," +
                    DatabaseSchema.HomeworksTable.Cols.DELIVERY_DATE + " TEXT)";

    private static final String SQL_DELETE_HOMEWORKS_TABLE =
            "DROP TABLE IF EXISTS " + DatabaseSchema.HomeworksTable.TABLE_NAME;

    public SQLiteOpenHelper(Context context) {
        super(context, context.getApplicationInfo().dataDir + "/databases/" + DatabaseSchema.DATABASE_NAME, null, DatabaseSchema.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACTIVITIES_TABLE);
        db.execSQL(SQL_CREATE_HOMEWORKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO need to perform actions to save db data before update schema
        db.execSQL(SQL_DELETE_ACTIVITIES_TABLE);
        db.execSQL(SQL_DELETE_HOMEWORKS_TABLE);
        onCreate(db);
    }
}
