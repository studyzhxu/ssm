package com.zhxu.ssm.controller;

import com.zhxu.ssm.entity.TestTb;
import com.zhxu.ssm.service.TestTbService;
import com.zhxu.ssm.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/16.
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private TestTbService testTbService ;

    @RequestMapping("/add")
    public String addTest(){
        TestTb tb = new TestTb();
        tb.setName("zhangsan1");
        tb.setBirthday(new Date());
        testTbService.insertTestTb(tb);
        return "/index";
    }

    @RequestMapping("/list")
    public Response list(){
        return new Response().success(testTbService.find());
    }

}
