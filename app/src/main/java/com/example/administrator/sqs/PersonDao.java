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
        Log.e("ddd", person.getName());
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", person.getName());
        contentValues.put("age", person.getAge());
        contentValues.put("sex", person.getSex());
        contentValues.put("code", person.getCode());
        db.insert("person", "_id", contentValues);
    }

    public void deletePerson(Person person) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.delete("person", "_id=?", new String[]{String.valueOf(person.get_id())});
    }

    public void updatePerson(Person person) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", person.getName());
        contentValues.put("age", person.getAge());
        contentValues.put("sex", person.getSex());
        contentValues.put("code", person.getCode());
        db.update("person", contentValues, "id=?", new String[]{String.valueOf(person.get_id())});
    }

    public List<Person> getAllPerson() {
        List<Person> persons = new ArrayList<Person>();
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = db.query("person", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            String code = cursor.getString(cursor.getColumnIndex("code"));
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            Person person = new Person(name, age, sex, code);
            person.set_id(_id);
            persons.add(person);

        }
        return persons;
    }

}
