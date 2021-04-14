package com.ankus.openapi.controller;

import com.ankus.openapi.service.CovidService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Controller
@RequestMapping("/")
public class CovidController2 {
    @Autowired
    CovidService2 covidService;
    private static Logger logger = LoggerFactory.getLogger(CovidController2.class);


    @RequestMapping(value = "/covidhome", method = {RequestMethod.GET, RequestMethod.POST})
    //@ResponseBody
    public String getData(Model model){
        model.addAttribute("dates",covidService.getDateList());
        model.addAttribute("totalConfirmed", covidService.getTotalConfirmed());
        model.addAttribute("increaseFromYesterday",covidService.getDecideList());
        model.addAttribute("dailyCure", covidService.getClearList());
        System.out.println("getData model> "+model);
        return model.toString();
    }

    @RequestMapping("/page1")
    public String setapi(){
        System.out.println("page1");

        return "page14.html";
    }


    @RequestMapping(value = "/apitest", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String callapi(){
        StringBuffer result = new StringBuffer();
        try{
            String urlstr = "http://openapi.data.go.kr/"+
                    "openapi/service/rest/Covid19/getCovid19InfStateJson"+
                    "?serviceKey"+
                    "&pageNo=1"+
                    "&numOfRows=10"+
                    "&startCreateDt=20200310&endCreateDt=20200315";

            URL url = new URL(urlstr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));

            String returnLine;

            while ((returnLine = br.readLine()) != null){
                result.append(returnLine+"\n");
                System.out.println("readLine > "+br.readLine());
            }
            urlConnection.disconnect();
            System.out.println("result > "+result);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result.toString();
        //return "index2";

    }





}
