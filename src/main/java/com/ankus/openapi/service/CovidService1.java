package com.ankus.openapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.net.URI;

@Service
@Component
public class CovidService1 {

    @Value("${openapi.service.key}")
    String OPENAPI_KEY;

    @Value("${openapi.service.url}")
    String OPENAPI_URL;

    public ResponseEntity<String> getApi(){
        String url = OPENAPI_URL + "?serviceKey="+OPENAPI_KEY
                +"&pageNo=1&numOfRows=10&startCreateDt=20200310&endCreateDt=20200315";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URI.create(url), HttpMethod.GET,entity,String.class);

        return response;
    }

    public Response parser(String xml){
        ObjectMapper xmlMapper = new ObjectMapper(); //XMLMapper
        Response response = null;
        try {
            response = xmlMapper.readValue(xml, Response.class);
        }catch(JsonMappingException e){
            e.printStackTrace();
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }

        return response;
    }

}
