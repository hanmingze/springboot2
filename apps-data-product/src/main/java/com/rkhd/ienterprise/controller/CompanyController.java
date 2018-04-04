package com.rkhd.ienterprise.controller;

import com.rkhd.ienterprise.entity.Employee;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/search")
public class SearchController {

    @ApiOperation(value="获取图书列表", notes="获取图书列表")
    @GetMapping("")
    public List<Employee> comp() {
        List<Employee> book = new ArrayList<>(books.values());
        return book;
    }

}
