package com.ankus.openapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class CovidController7 {

    @GetMapping("/page7")
    public String setpage7() throws Exception{
        System.out.println("page7 sout");
        return "page7";
    }



    @RequestMapping(value="/getparam7",method =  RequestMethod.POST)
    @ResponseBody
    //Map<String,String> // @RequestParam LinkedHashMap params
    // String // @RequestParam String params
    public Map<String,String> getParam7(HttpServletRequest request,@RequestParam LinkedHashMap params) throws Exception {
        System.out.println("params7 > " + params );

        return params;
    }



    @RequestMapping(value="/callurl7",method =  RequestMethod.POST)
    @ResponseBody
    public String callurl7(HttpServletRequest request, @RequestParam LinkedHashMap params) throws Exception {
        System.out.println("callurl7 params > " + params );
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(params);

        String kv ="";
        StringBuffer result = new StringBuffer();
        for (String key: map.keySet()) {
            String value = map.get(key);
            System.out.println("key > " + key + ", value >" + value);
            kv = kv + "&" + key + "=" + value;
//            if (key == "addrs" || key == "servicekey") {
//                kv = kv;
//            } else {
//                kv = kv + "&" + key + "=" + value;
//            }
        }
        System.out.println("map sizee > "+ map.size() + ", kv > "+ kv);

            try{
                kv = kv.substring(1);
                System.out.println("kv > "+ kv);
                URL urls = new URL(kv);

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


        return result.toString();
    }





}
