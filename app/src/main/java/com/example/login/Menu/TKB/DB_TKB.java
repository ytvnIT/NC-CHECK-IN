package com.example.login.Menu.TKB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DB_TKB extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME ="app";
    private static final String TABLE_NAME ="tkb";
    private static final String ID ="id";
    private static final String THU ="thu";
    private static final String MAMH ="mamh";
    private static final String TIET ="tiet";
    private static final String GV ="gv";
    private static final String PHONG ="phong";
    private static final int VERSION = 1;
    public DB_TKB(@Nullable Context context) {
        super(context,DATABASE_NAME,null,VERSION);
        //this.context = context;
    }
    String sqlQuery = "CREATE TABLE "+ TABLE_NAME +" ("+
            ID + " integer primary key, "+
            MAMH + " TEXT, " +
            TIET + " TEXT, "+
            GV + " TEXT, "+
            THU + " integer, "+
            PHONG + " TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlQuery);

        //Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void clearTable(){
//        if (VERSION == 1){
//            this.VERSION = 2;
//        }else if (VERSION==2) {
//            this.VERSION = 1;
//        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    public void addTKB (TKB student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MAMH,student.getMamh());
        values.put(GV,student.getName());
        values.put(TIET,student.getTiet());
        values.put(PHONG,student.getPhong());
        values.put(THU,student.getId());
        db.insert(TABLE_NAME,null,values);
        db.close();
        Log.d(TAG,"addStudent Successfuly");
    }
    public List<TKB> getTKB(int day){
        List<TKB> list = new ArrayList<TKB>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE THU = '" +day + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TKB student = new TKB();
                student.setMamh(cursor.getString(1));
                student.setTiet(cursor.getString(2));
                student.setName(cursor.getString(3));
                student.setId(cursor.getInt(4));
                student.setPhong(cursor.getString(5));
                list.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
