package com.ankus.openapi.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Data
public class CovidService2 {

    private List<Integer> decideList; //날짜 별 확진자 수
    private List<Integer> deathList; //날자 별 사망자 수
    private List<Integer> clearList; //날짜별 격리해제 수
    private List<String> dateList; //날자별 문자열 배열
    private int totalConfirmed; //오늘까지 누적 총 확진자

    public String getStartCreateDt(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = now.minusDays(11);
        return dtf.format(then);
    }

    public String getEndCreateDt(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = now.minusDays(11);
        return dtf.format(then);
    }

    public void initialDateList(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd");
        LocalDateTime now = LocalDateTime.now();
        for (int i=9; i>=0; i--){
            LocalDateTime date = now.minusDays(i);
            dateList.add(dtf.format(date));
        }
    }

    private String getTagValue(String tag, Element ele){
        NodeList nodeList = ele.getElementsByTagName(tag).item(0).getChildNodes();

        Node nValue = (Node) nodeList.item(0);

        if(nValue == null){
            return null;
        }
        return nValue.getNodeValue();
    }


    public void xmlApiTest(String url){
        decideList = new ArrayList<>();
        deathList = new ArrayList<>();
        clearList = new ArrayList<>();
        dateList = new ArrayList<>();

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");

            boolean flag = true;
            for(int temp=0; temp<nodeList.getLength()-2; temp++){
                Node nNode = nodeList.item(temp);
                Node nNodeTest = nodeList.item(temp+1);

                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)nNode;
                    Element elementTest = (Element)nNodeTest;

                    decideList.add(Integer.parseInt(getTagValue("decideCnt",element))-
                            Integer.parseInt(getTagValue("decideCnt",elementTest)));
                    deathList.add(Integer.parseInt(getTagValue("deathCnt",element))-
                            Integer.parseInt(getTagValue("deathCnt",elementTest)));
                    clearList.add(Integer.parseInt(getTagValue("clearCnt",element))-
                            Integer.parseInt(getTagValue("clearCnt",elementTest)));


                    if(flag){
                        totalConfirmed = Integer.parseInt(getTagValue("decideCnt",element));
                        flag=false;
                    }

                }
            }

            initialDateList();

        }catch (Exception e){
            System.out.println(e);
        }

        Collections.reverse(decideList);
        Collections.reverse(deathList);
        Collections.reverse(clearList);
    }



    //xmlApitest메소드에 url 파라미터 넘겨주고 호출함
    // shceduled 로 매일 한번씩 자동 호출 됨
    @PostConstruct //어노테이션 있는 메소드를 자동으로 실행시켜줌
    @Scheduled(cron="* * 1 * * *") //application 에 @EnableScheduling 추가 필수
    public void initialData(){
        try {
            /*URL*/
            StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/"+
                    "openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") +
                    ""); /*Service Key*/
            /*공공데이터포털에서 받은 인증키*/
            urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") +
                    "=" + URLEncoder.encode("", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") +
                    "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") +
                    "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            /*검색할 생성일 범위의 시작*/
            urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") +
                    "=" + URLEncoder.encode(getEndCreateDt(), "UTF-8"));
            /*검색할 생성일 범위의 종료*/
            urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") +
                    "=" + URLEncoder.encode(getStartCreateDt(), "UTF-8"));
            URL url = new URL(urlBuilder.toString());
            xmlApiTest(url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300){
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }else{
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

        }catch (Exception e){
            System.out.println(e);
        }

    }






}
