package com.wdbyte.rmi.server;

import java.io.Serializable;

/**
 *
 * @author www.wdbyte.com
 * @date 2021/05/08
 */
public class User implements Serializable {

    private static final long serialVersionUID = 6490921832856589236L;

    private String name;
    private Integer age;
    private String skill;

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

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "User{" +
            "name='" + name + '\'' +
            ", age=" + age +
            ", skill='" + skill + '\'' +
            '}';
    }
}
