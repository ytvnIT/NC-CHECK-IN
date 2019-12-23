package com.example.login.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.login.Menu.CheckIn.CheckInList;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public  void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    public void deleteAll(){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM DiemDanh");
    }
    public void insert(ArrayList<CheckInList> checkInLists){
        SQLiteDatabase database = getWritableDatabase();
        for (CheckInList checkInList : checkInLists) {
            String MAMH = checkInList.getMAMH();
            String MAHV = checkInList.getMAHV();
            String time = checkInList.getTime();
            database.execSQL("INSERT INTO DiemDanh VALUES(null, '" + MAMH + "', '" + time +"', '" + MAHV +"')" );
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}