package com.example.lukyanov_laba3_part1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "studentsDb";
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_STUDENTS_BACKUP = "students_backup";
    public static final String KEY_ID = "_id";
    public static final String KEY_FIO = "fio";
    public static final String KEY_LNAME = "lname";
    public static final String KEY_FNAME = "fname";
    public static final String KEY_SNAME = "sname";
    public static final String KEY_TIME = "time";
    String [] fiosplit;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, MainActivity.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_STUDENTS + "(" + KEY_ID + " integer primary key," + KEY_FIO + " text," + KEY_TIME + " text" + ")");
        //получение ФИО
        String fio = "Фамилия Имя Отчество";
        //получение даты
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String time = format.format(new Date());
        ContentValues contentValues = new ContentValues();
        //заполнение данными
        for (int i = 0; i < 5; i++) {
            contentValues.put(KEY_FIO, fio);
            contentValues.put(KEY_TIME, time);
            db.insert(TABLE_STUDENTS, null, contentValues);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.beginTransaction();
            try{
                db.execSQL("create table " + TABLE_STUDENTS_BACKUP + "(" + KEY_ID + " integer primary key, " + KEY_FNAME + " text, " + KEY_LNAME + " text, " + KEY_SNAME + " text, " + KEY_TIME + " text" + ")");
                Cursor cursor = db.query(DBHelper.TABLE_STUDENTS, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int fioIndex = cursor.getColumnIndex(DBHelper.KEY_FIO);
                    int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
                    do {
                        fiosplit = cursor.getString(fioIndex).split(" ", 3);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(KEY_ID, cursor.getString(idIndex));
                        contentValues.put(KEY_FNAME, fiosplit[0]);
                        contentValues.put(KEY_LNAME, fiosplit[1]);
                        contentValues.put(KEY_SNAME, fiosplit[2]);
                        contentValues.put(KEY_TIME, cursor.getString(timeIndex));
                        db.insert(TABLE_STUDENTS_BACKUP, null, contentValues);
                        Log.d("mLog", "fio = " + cursor.getString(fioIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");
                db.execSQL("drop table " + TABLE_STUDENTS);
                db.execSQL("alter table " + TABLE_STUDENTS_BACKUP + " rename to " + TABLE_STUDENTS);
                cursor.close();
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }
}