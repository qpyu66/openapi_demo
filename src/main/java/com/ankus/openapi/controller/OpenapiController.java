//package com.ankus.openapi.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Controller
//public class OpenapiController {
//
//    @GetMapping("/open1")
//    public String open1() throws Exception{
//        System.out.println("open1 sout");
//        return "open1";
//    }
//
//
//
//
//    @RequestMapping(value="/openparam1",method =  RequestMethod.POST)
//    @ResponseBody
//    public Map<String,String> openParam1(HttpServletRequest request, @RequestParam LinkedHashMap params) throws Exception {
//        System.out.println("params > " + params + ", request > " + request);
//        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(params);
//
//        String kv = "";
//        //json 으로 받은 key, value 출력
//        for (String key : map.keySet()) {
//            String value = map.get(key);
//            System.out.println("map key > " + key + " , value > " + value);
//            if (key == "addrs" || key == "servicekey") {
//                kv = kv;
//            } else {
//                kv = kv + "&" + key + "=" + value;
//            }
//
//        }
//
//        System.out.println("map sizee > " + map.size() + ", kv > " + kv);
//
//        String addrs = request.getParameter("addrs");
//        String sk = request.getParameter("servicekey");
//        System.out.println("map sizee > " + map.size() + " ,addr > " + addrs + " ,sk > " + sk + " , kv > " + kv);
//
//
//
//        return params;
//    }
//
//
//
//
//
//
//    }
