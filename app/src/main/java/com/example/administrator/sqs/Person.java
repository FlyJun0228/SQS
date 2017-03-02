package com.example.administrator.sqs;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/2.
 */

public class Person implements Serializable {

    private String name;
    private int age;
    private String code;
    private String sex;

    public String getSex(){
        return sex;
    }
    public void setSex(String sex){
        this.sex=sex;
    }
    private String first_letter;
    private int _id;
    public void set_id(int _id){
        this._id=_id;
    }
    public int get_id(){
        return _id;
    }
    public void setFirst_letter(String first_letter){
        this.first_letter = first_letter;
    }
    public String getFirst_letter(){
        return first_letter;
    }
    public Person(){

    }
    public Person(String name,int age,String code){
        this.name=name;
        this.age=age;
        this.code=code;
    }
    public Person(String name, int age, String code, String sex){
        this.name=name;
        this.age=age;
        this.code=code;
        this.sex=sex;
    }
    public String toString(){
        return name+""+age+""+code;
    }
    public void setAge(int age){
        this.age=age;
    }
    public void setCode(String code){
        this.code=code;
    }
    public void setName(String name){
        this.name=name;
    }
    public int getAge(){
        return age;
    }
    public String getCode(){
        return code;
    }
    public String getName(){
        return name;
    }
}
