package com.example.administrator.sqs;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/2.
 */

public class Person implements Serializable {

    private int _id;
    private String name;
    private String major;
    private String gender;
    private String interest;
    private String collect;

    public Person() {

    }

    public Person(String name, String major, String gender, String interest, String collect) {
        this.name = name;
        this.major = major;
        this.gender = gender;
        this.interest = interest;
        this.collect = collect;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }
}