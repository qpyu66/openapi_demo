package com.ankus.openapi.controller;

import com.ankus.openapi.service.CovidService1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/")
public class CovidController1 {

    CovidService1 covidService1;

    private static Logger logger = LoggerFactory.getLogger(CovidController1.class);


    public CovidController1(CovidService1 covidService1){
        this.covidService1 = covidService1;
    }

    @RequestMapping(value="covid", method= {RequestMethod.GET}) //, RequestMethod.POST
    @ResponseBody
    public void getCovid(){
        ResponseEntity<String> responseEntity = covidService1.getApi();
        Response response = (Response) covidService1.parser(responseEntity.getBody());

        System.out.println("res > "+response);

    }




}
