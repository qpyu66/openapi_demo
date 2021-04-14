package com.ankus.openapi.controller;

import com.ankus.openapi.vo.listVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Controller
@RequestMapping("/")
public class CovidController4 {

    private static Logger logger = LoggerFactory.getLogger(CovidController4.class);


    //@RequestMapping(value = "/page4", method = {RequestMethod.GET, RequestMethod.POST})
    @GetMapping("/page4")
    public String setpage() throws Exception{
        System.out.println("page4 sout");
        logger.debug("page4");
        return "page4";
    }

    @RequestMapping(value="/getjson1")
    @ResponseBody
    public String getParam(listVO vo, Model model){
        String result = "";
        System.out.println("url > "+vo.getAddrs());
        System.out.println("servicekey > "+vo.getServiceKey());
        System.out.println("numofrows > "+vo.getNumOfRows());
        System.out.println("pageno > "+vo.getPageNo());

        model.addAttribute("url",vo.getAddrs());
        model.addAttribute("servicekey",vo.getServiceKey());
        model.addAttribute("numofrows",vo.getNumOfRows());
        model.addAttribute("pageno",vo.getPageNo());
        System.out.println("model>"+model);
        return result;
    }


    //ok
    @RequestMapping(value="/getjson3",method =  RequestMethod.POST)
    @ResponseBody
    public String getJson3(HttpServletRequest request) throws IOException {
       String addrs = request.getParameter("addrs");
       String sk = request.getParameter("servicekey");
       String pn = request.getParameter("pageno");
       String rn = request.getParameter("numofrows");

       System.out.println("urls > "+addrs+", "+sk+", "+rn+", "+pn);

        StringBuffer result = new StringBuffer();
        try{

            String parameter;
            parameter = addrs + "?serviceKey="+sk+"";
            parameter = parameter+"&pageNo="+pn+"&numOfRows="+rn;
            System.out.println("parameter > "+parameter);

            URL urls = new URL(parameter);
            HttpURLConnection urlConnection = (HttpURLConnection) urls.openConnection();
            urlConnection.setRequestMethod("GET");
            System.out.println("urls> "+urls);
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

        System.out.println("getJson3 > "+result.toString());
        return result.toString();

    }



}
