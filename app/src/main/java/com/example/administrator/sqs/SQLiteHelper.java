package com.example.administrator.sqs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/3/2.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    String create_TABLE_sql = "create table person(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "name varchar(30),age integer,sex varchar(30),code varchar(30))";
    String delete_sql = "detele from person";


    public SQLiteHelper(Context context) {
        super(context, "mysq.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table alarm(_id integer primary key autoincrement,name varchar(30),hour integer,minute integer)");
        db.execSQL(create_TABLE_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table person");
        db.execSQL("drop table alarm");
        db.execSQL(create_TABLE_sql);
        db.execSQL("drop table alarm");
        db.execSQL("create table alarm(_id integer primary key autoincrement,name varchar(30),hour integer,minute integer)");
    }
}
