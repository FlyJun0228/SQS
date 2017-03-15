package com.example.administrator.sqs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class PersonDao {

    private Context context;
    private SQLiteHelper sqLiteHelper;

    public PersonDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    public void addPerson(Person person) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("major_name", person.getMajor());
        contentValues.put("user_name", person.getName());
        contentValues.put("gender", person.getGender());
        contentValues.put("interests", person.getInterest());
        contentValues.put("collect", person.getCollect());
        db.insert("person", "_id", contentValues);
    }

    public void deletePerson(Person person) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.delete("person", "_id=?", new String[]{String.valueOf(person.get_id())});
    }

    public void updatePerson(Person person) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("major_name", person.getMajor());
        contentValues.put("user_name", person.getName());
        contentValues.put("gender", person.getGender());
        contentValues.put("interests", person.getInterest());
        contentValues.put("collect", person.getCollect());
        db.update("person", contentValues, "id=?", new String[]{String.valueOf(person.get_id())});
    }

    public List<Person> getAllPerson() {
        List<Person> persons = new ArrayList<Person>();
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = db.query("person", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String major = cursor.getString(cursor.getColumnIndex("major_name"));
            String name = cursor.getString(cursor.getColumnIndex("user_name"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String interest = cursor.getString(cursor.getColumnIndex("interests"));
            String collect = cursor.getString(cursor.getColumnIndex("collect"));
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            Person person = new Person(name, major, gender, interest, collect);
            person.set_id(_id);
            persons.add(person);

        }
        return persons;
    }

}
