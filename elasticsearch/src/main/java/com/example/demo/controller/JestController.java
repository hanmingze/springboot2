package com.example.demo.controller;

import com.example.demo.service.JestService;
import io.searchbox.client.JestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiaozhiguang on 2018/3/31.
 */
@RestController
@RequestMapping("/jest")
public class JestController {

    @Autowired
    private JestService jestService;


}
