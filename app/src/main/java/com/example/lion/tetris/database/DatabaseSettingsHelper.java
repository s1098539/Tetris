package com.example.lion.tetris.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.lion.tetris.highscore.Highscore;
import com.example.lion.tetris.model.Settings;

/**
 * Created by lion on 10/03/2018.
 */

public class DatabaseSettingsHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mSQLDB;
    private static DatabaseSettingsHelper mInstance;
    public static final String dbName = "TetrisLocalSettings.db";
    public static final int dbVersion = 4;

    private DatabaseSettingsHelper(Context ctx) {
        super(ctx, dbName, null, dbVersion);
    }

    public static synchronized DatabaseSettingsHelper getHelper(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseSettingsHelper(ctx);
            mSQLDB = mInstance.getWritableDatabase();
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseSettingsInfo.LocalSettingsTables.LOCALSETTINGS + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseSettingsInfo.LocalSettingsColumn.I + " TEXT," +
                DatabaseSettingsInfo.LocalSettingsColumn.J + " TEXT," +
                DatabaseSettingsInfo.LocalSettingsColumn.L + " TEXT," +
                DatabaseSettingsInfo.LocalSettingsColumn.O + " TEXT," +
                DatabaseSettingsInfo.LocalSettingsColumn.S + " TEXT," +
                DatabaseSettingsInfo.LocalSettingsColumn.T + " TEXT," +
                DatabaseSettingsInfo.LocalSettingsColumn.Z + " TEXT);"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseSettingsInfo.LocalSettingsTables.LOCALSETTINGS);
        onCreate(db);
    }

    public DatabaseSettingsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void insert(String table, String nullColumnHack, ContentValues values) {
        mSQLDB.insert(table, nullColumnHack, values);
    }

    public void update(String table, String where, ContentValues values) {
        mSQLDB.update(table,values,where,null);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy) {
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }
}

