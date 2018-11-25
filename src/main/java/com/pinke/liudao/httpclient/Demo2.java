package com.pinke.liudao.httpclient;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;
// Fluent HttpClient
public class Demo2 {
    public static void main(String[] args) throws IOException {
        Content content = Request.Post("http://192.168.43.133:10002/account/login")
                //.bodyString("{\"x\"=\"xx\",\"xx\"=\"xx\"}",ContentType.APPLICATION_JSON)
                .bodyForm(
                        Form.form()
                            .add("username","liudao001")
                            .add("password","123456")
                            .build()
                )
                .execute()
                .returnContent();
        System.out.println(content.asString());
    }
}
