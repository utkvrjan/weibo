package com.zlt.weibo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class IPAddress {

    /**
     * 根据ip地址获取实际地址
     * 使用java发送一个http请求出去，获取返回结果
     *
     * @return
     * {"status":"success","country":"中国","countryCode":"CN","region":"BJ",
     * "regionName":"北京市","city":"北京","zip":"",】
     * "lat":39.9042,"lon":116.407,"timezone":"Asia/Shanghai","isp":"GWBN-WUHAN's IP",
     * "org":"","as":"AS7497 Computer Network Information Center","query":"115.191.200.34"}
     *
     * json 本质上就是一个字符串  只不过这个字符串的格式有要求 很多语言都可以识别
     *  http://ip-api.com/json/115.191.200.34?lang=zh-CN
     *
     *  java模拟http请求一般用httpClient比较多
     *
     */
    public static String getIPAddress(String ip){
        URL url = null;
        HttpURLConnection conn = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL("http://ip-api.com/json/"+ip+"?lang=zh-CN");
            //打开连接
            conn = (HttpURLConnection)url.openConnection();
            //获取响应
            InputStream is = conn.getInputStream();
            isr = new InputStreamReader(is,"UTF-8");
            br = new BufferedReader(isr);
            String str = null;
            while((str = br.readLine()) != null){
                sb.append(str);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(isr != null){
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(conn != null){
                //断开连接
                conn.disconnect();
            }
        }
        return sb.toString();
    }
}
