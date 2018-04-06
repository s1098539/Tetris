package com.example.lion.tetris.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by lion on 10/03/2018.
 */

public class DatabaseHighscoreHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mSQLDB;
    private static DatabaseHighscoreHelper mInstance;
    public static final String dbName = "TetrisLocalHighscores.db";
    public static final int dbVersion = 4;

    private DatabaseHighscoreHelper(Context ctx) {
        super(ctx, dbName, null, dbVersion);
    }

    public static synchronized DatabaseHighscoreHelper getHelper(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseHighscoreHelper(ctx);
            mSQLDB = mInstance.getWritableDatabase();
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseHighscoreInfo.LocalHighscoreTables.LOCALHIGHSCORE + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseHighscoreInfo.LocalHighscoreColumn.NAME + " TEXT," +
                DatabaseHighscoreInfo.LocalHighscoreColumn.SCORE + " TEXT," +
                DatabaseHighscoreInfo.LocalHighscoreColumn.LEVEL + " TEXT," +
                DatabaseHighscoreInfo.LocalHighscoreColumn.DATE + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHighscoreInfo.LocalHighscoreTables.LOCALHIGHSCORE);
        onCreate(db);
    }

    public DatabaseHighscoreHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void insert(String table, String nullColumnHack, ContentValues values) {
        mSQLDB.insert(table, nullColumnHack, values);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy) {
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }
}

