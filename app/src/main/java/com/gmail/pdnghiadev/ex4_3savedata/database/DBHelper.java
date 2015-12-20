package com.gmail.pdnghiadev.ex4_3savedata.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PDNghiaDev on 12/20/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    // Tên và phiên bản của CSDL, tên của các Table trong CSDL
    private static final String DATABASE_NAME = "Highscore.db";
    private static final int DATABASE_VERSION = 1;
    private static final String HIGHSCORE_TABLE = "HighscoreTable";

    // Tên cột khóa chính trong Table
    public static final String HIGHSCORE_COLUMN_ID = "id";

    // Tên và chỉ số của cột "name" trong Table "HighscoreTable"
    public static final String HIGHSCORE_COLUMN_DATE_CREATE = "DateCreate";
    public static final String HIGHSCORE_COLUMN_COUNT_TAP = "CountTap";

    // Câu lệnh tạo Table "HighscoreTable" trong CSDL
    public static final String DATABASE_CREATE = "CREATE TABLE " + HIGHSCORE_TABLE
            + "(" + HIGHSCORE_COLUMN_ID + " INT PRIMARY KEY, "
            + HIGHSCORE_COLUMN_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " // CURRENT_TIMESTAMP - Inserts both time and date
            + HIGHSCORE_COLUMN_COUNT_TAP + " INT NOT NULL);";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HIGHSCORE_TABLE);
        onCreate(db);
    }
}
