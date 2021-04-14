package com.ankus.openapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenapiController {

    @GetMapping("/open1")
    public String setpage5() throws Exception{
        System.out.println("open1 sout");
        return "open1";
    }








}
