package com.merryituxz.tablegenerator.entity;


import com.merryituxz.tablegenerator.annotations.AutoIncrement;
import com.merryituxz.tablegenerator.annotations.Length;
import com.merryituxz.tablegenerator.annotations.NotNull;
import com.merryituxz.tablegenerator.annotations.PrimaryKey;

import java.util.Date;

public class User {
    @AutoIncrement
    @PrimaryKey
    @NotNull
    private Integer id;

    @Length(24)
    @NotNull
    private String name;

    @Length(2)
    private Integer age;

    @NotNull
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
