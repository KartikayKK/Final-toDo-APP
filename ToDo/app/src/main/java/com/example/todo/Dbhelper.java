package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Dbhelper extends SQLiteOpenHelper {

    private static final String name ="Kartikay";
    private static final int n =5;
    public static final String Ktable="Task";
    public static final String Kcolumn="TaskName";

    public Dbhelper(Context context) {

        super(context, name, null,n);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    String query= String.format("CREATE TABLE  %s(ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);",Ktable,Kcolumn);
    db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query=String.format("DELETE TABLE IF EXISTS %s",Ktable);
        db.execSQL(query);
        onCreate(db);

    }

    public void insert(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Kcolumn, task);
        db.insertWithOnConflict(Ktable, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }
    public void delete(String task){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(Ktable,Kcolumn+"=\""+task+"\"",null);
        db.close();
    }
    public ArrayList<String> getTaskList(){
        ArrayList<String> tasklist=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(Ktable,new String[]{Kcolumn},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index =cursor.getColumnIndex(Kcolumn);
            tasklist.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return tasklist;
    }
}

