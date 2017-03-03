package com.example.administrator.sqs;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/2.
 */

public class Person implements Serializable {

    private int age;
    private int _id;
    private String name;
    private String code;
    private String sex;
    private String first_letter;

    public Person() {

    }

    public Person(String name, int age, String sex, String code) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.code = code;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }
}
