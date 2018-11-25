package com.pinke.liudao.origin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Demo {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://192.168.43.133:10002/account/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Cookie","xxx=xxx;xxx=xxx");
        OutputStream os = connection.getOutputStream();
        os.write("username=liudao001&password=123456".getBytes("utf-8"));
        os.flush();
        System.out.println("Response Code: "+connection.getResponseCode());
        System.out.println("Response Message: "+connection.getResponseMessage());
        System.out.println("Content Type: "+connection.getContentType());
        InputStream is = connection.getInputStream();
        Reader reader = new InputStreamReader(is,"utf-8");
        int t = -1;
        StringBuffer sb = new StringBuffer();
        while((t = reader.read()) != -1){
            sb.append((char) t);
        }
        System.out.println("Content: "+sb);
        reader.close();
        is.close();
        os.close();
        connection.disconnect();
    }
}
