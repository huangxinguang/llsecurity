package com.laile.security.core.util;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/10/10 下午4:35.
 */
public class LngAndLatUtil {

    public static Map<String,Double> getLngAndLat(String address){
        Map<String,Double> map=new HashMap<>();
        String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=F454f8a5efe5e577997931cc01de3974";
        String json = loadJSON(url);
        JSONObject obj = JSONObject.parseObject(json);
        if(obj.get("status").toString().equals("0")){
            double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
            double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
            map.put("lng", lng);
            map.put("lat", lat);
        }else{
            map.put("lng", 89.52089138875085);//默认火焰山 金纬度
            map.put("lat", 42.92520032506515);
        }
        return map;
    }

    public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }
}
