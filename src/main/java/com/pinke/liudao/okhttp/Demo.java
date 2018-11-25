package com.pinke.liudao.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Demo {
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .followRedirects(false)
            .build();
    public static void main(String[] args) throws IOException {
//        MediaType formType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody.create(formType, "username=liudao001&password=123456");

        RequestBody body = new FormBody.Builder()
                .add("username", "liudao001")
                .add("password","123456")
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.43.133:10002/account/login")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
