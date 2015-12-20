package com.gmail.pdnghiadev.ex4_3savedata.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.pdnghiadev.ex4_3savedata.model.ResultItem;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PDNghiaDev on 12/20/2015.
 */
public class DBAdapter {
    // Tên và phiên bản của CSDL
    private static final String DATABASE_NAME = "Highscore.db";
    private static final int DATABASE_VERSION = 1;
    private static final String HIGHSCORE_TABLE = "HighscoreTable";

    // Tên cột khóa chính trong Table
    public static final String HIGHSCORE_COLUMN_ID = "id";

    // Tên và chỉ số của cột "name" trong Table "HighscoreTable"
    public static final String HIGHSCORE_COLUMN_DATE_CREATE = "DateCreate";
    public static final String HIGHSCORE_COLUMN_COUNT_TAP = "CountTap";

    private SQLiteDatabase db;
    private Context mContext;
    private DBHelper dbHelper;

    public DBAdapter(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Mở Adapter
    public DBAdapter open() throws SQLException{
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // Đóng Adapter
    public void close() {
        db.close();
    }

    // Chèn dữ liệu vào Table
    public boolean insertResultItem(Date date, int count) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(HIGHSCORE_COLUMN_DATE_CREATE, convertDateToString(date));
        contentValues.put(HIGHSCORE_COLUMN_COUNT_TAP, count);
        db.insert(HIGHSCORE_TABLE, null, contentValues);
        return true;
    }

    // Remove dữ liệu từ Table
    public void removeHighscoreList() {
//        db.execSQL("DELETE FROM " + HIGHSCORE_TABLE);
        db.delete(HIGHSCORE_TABLE, null, null);
    }

    // Lấy danh sách dữ liệu
    public List<ResultItem> getAllHighscore() throws ParseException {
        List<ResultItem> list = new ArrayList<>();

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + HIGHSCORE_TABLE, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(new ResultItem(convertStringToDate(cursor.getString(cursor.getColumnIndex(HIGHSCORE_COLUMN_DATE_CREATE))),
                    cursor.getInt(cursor.getColumnIndex(HIGHSCORE_COLUMN_COUNT_TAP))));
            cursor.moveToNext();
        }

        return list;
    }

    public String convertDateToString(Date date){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return format.format(date);
    }

    public Date convertStringToDate(String string) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return format.parse(string);
    }
}
