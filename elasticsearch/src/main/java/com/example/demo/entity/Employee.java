package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by jiaozhiguang on 2018/3/31.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee<T> {

    private String firstName;
    private String lastName;
    private int age;
    private String about;
    private List<String> interests;

}
