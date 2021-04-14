package com.ankus.openapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class CovidController5 {

    @GetMapping("/page5")
    public String setpage5() throws Exception{
        System.out.println("page5 sout");
        return "page5";
    }

    @GetMapping("/page51")
    public String setpage51() throws Exception{
        System.out.println("page51 sout");
        return "page5_1";
    }


    @RequestMapping(value="/getparam1",method =  RequestMethod.POST)
    @ResponseBody
    public Map<String,String> getParam1(HttpServletRequest request, @RequestParam LinkedHashMap params) throws Exception {
        System.out.println("params > "+params+", request > "+request);
        LinkedHashMap<String, String> map = new LinkedHashMap<String,String>(params);

        String kv = "";
        //json 으로 받은 key, value 출력
        for (String key : map.keySet()){
            String value = map.get(key);
            System.out.println("map key > "+key+" , value > "+value);
            if(key == "addrs" || key == "servicekey"){
                kv = kv;
            }else{
                kv = kv+"&"+key+"="+value;
            }

        }

        System.out.println("map sizee > "+ map.size() + ", kv > "+ kv);

        String addrs = request.getParameter("addrs");
        String sk = request.getParameter("servicekey");
        System.out.println("map sizee > "+ map.size() +" ,addr > "+addrs+" ,sk > "+sk+ " , kv > "+ kv);

        return params;



//        String addrs = request.getParameter("addrs");
//        String sk = request.getParameter("servicekey");
//        String pn = request.getParameter("pageno");
//        String rn = request.getParameter("numofrows");
//
//        System.out.println("urls > "+addrs+", "+sk+", "+rn+", "+pn);
//
//        StringBuffer result = new StringBuffer();
//        try{
//
//            String parameter;
//            parameter = addrs + "?serviceKey="+sk+"";
//            parameter = parameter+"&pageNo="+pn+"&numOfRows="+rn;
//
//
//            URL urls = new URL(parameter);
//            HttpURLConnection urlConnection = (HttpURLConnection) urls.openConnection();
//            urlConnection.setRequestMethod("GET");
//            System.out.println("urls> "+urls);
//            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
//
//            String returnLine;
//
//            while ((returnLine = br.readLine()) != null){
//                result.append(returnLine+"\n");
//                System.out.println("readLine > "+br.readLine());
//            }
//            urlConnection.disconnect();
//            System.out.println("result > "+result);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        System.out.println("getJson3 > "+result.toString());
//        //return result.toString();
    }





}
