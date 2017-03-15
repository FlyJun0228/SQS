package com.example.administrator.sqs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/3/2.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    String create_TABLE_sql = "create table person(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "major_name varchar(30),user_name varchar(30),gender varchar(30),interests varchar(30),collect varchar(30))";


    public SQLiteHelper(Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_TABLE_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(create_TABLE_sql);
    }
}
