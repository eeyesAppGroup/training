package com.wx.myapplication;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by wanxu on 2016/10/17.
 * Email:wanxu_pursue@163.com
 */

public class User {
    //1.加注解将该成员映射至数据库中的字段，generateId=true表示主键
    @DatabaseField(generatedId = true)
    int id;
    //2.index表示排序依据
    @DatabaseField(index = true)
    String name;
    @DatabaseField
    int age;
    @DatabaseField
    String phone;
    //3.必须有一个空的构造方法
    public User() {
        // needed by ormlite
    }
    //4.所有映射到数据库的成员必须有set与get方法
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }

    public User(int id, String name, int age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }
    public User(String name, int age, String phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
