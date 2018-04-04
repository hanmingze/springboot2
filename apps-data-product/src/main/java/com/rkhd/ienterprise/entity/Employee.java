package com.example.demo.entity;

import io.searchbox.annotations.JestId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by jiaozhiguang on 2018/3/31.
 */

@Data
public class Employee<T> {

    @JestId
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String about;
    private List<String> interests;

    public Employee() {
    }

    public Employee(long id, String firstName, String lastName, int age, String about, List<String> interests) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.about = about;
        this.interests = interests;
    }

}
