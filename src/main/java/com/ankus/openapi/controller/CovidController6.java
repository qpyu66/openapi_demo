package com.ankus.openapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class CovidController6 {

    @GetMapping("/page6")
    public String setpage5() throws Exception{
        System.out.println("page6 sout");
        return "page6";
    }



    @RequestMapping(value="/getparam6",method =  RequestMethod.POST)
    @ResponseBody
    public Map<String,String> getParam6(HttpServletRequest request, @RequestParam LinkedHashMap params) throws Exception {
        System.out.println("params > " + params );
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(params);

        ModelAndView mav = new ModelAndView();
        mav.addObject("addrresult" , params);

        return params;
    }



    @RequestMapping(value="/callurl1",method =  RequestMethod.POST)
    @ResponseBody
    public String callurl1(HttpServletRequest request, @RequestParam LinkedHashMap params) throws Exception {
        System.out.println("callurl1 params > " + params );
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(params);

        StringBuffer result = new StringBuffer();
        for (String key: map.keySet()){
            System.out.println("key > "+key);
            try{
                URL urls = new URL(key);
                HttpURLConnection urlConnection = (HttpURLConnection) urls.openConnection();
                urlConnection.setRequestMethod("GET");
                //System.out.println("urls> "+urls);
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));

                String returnLine;

                while ((returnLine = br.readLine()) != null){
                    result.append(returnLine+"\n");
                    //System.out.println("readLine > "+br.readLine());
                }
                urlConnection.disconnect();
                System.out.println("result > "+result);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result.toString();
    }





}
