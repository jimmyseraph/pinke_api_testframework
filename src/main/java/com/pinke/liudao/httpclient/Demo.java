package com.pinke.liudao.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// HttpClient
public class Demo {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://192.168.43.133:10002/account/login");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("username","liudao002"));
        nvps.add(new BasicNameValuePair("password","123456"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine());
        System.out.println(Arrays.toString(response.getHeaders("Content-Type")));
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity));
        EntityUtils.consume(entity);
        response.close();
        httpClient.close();
    }
}
