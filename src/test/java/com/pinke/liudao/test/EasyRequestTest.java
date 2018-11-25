package com.pinke.liudao.test;

import com.jayway.jsonpath.JsonPath;
import com.pinke.liudao.core.EasyRequest;
import com.pinke.liudao.core.EasyResponse;
import com.pinke.liudao.core.okhttp.OkHttpEasyRequest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.io.IOException;

public class EasyRequestTest {
    @Test
    public void testRequest() throws IOException {
        EasyResponse response = new OkHttpEasyRequest()
                .setUrl("http://192.168.43.133:10002/account/login")
                .setMethod("post")
                .setBody(EasyRequest.MEDIA_TYPE_FORM_DATA, "username=liudao001&password=123456")
                .execute();
        String ret = response.getBody();
        System.out.println(ret);
        int retCode = JsonPath.read(ret, "$.retCode");
        assertEquals(retCode, 10000);
        String token = JsonPath.read(ret, "$.data.token");
        response = new OkHttpEasyRequest()
                .setUrl("http://192.168.43.133:10003/order/search")
                .setMethod("POST")
                .addHeader("Access-Token", token)
                .setBody(EasyRequest.MEDIA_TYPE_FORM_DATA, "amountLow=60&amountHigh=100")
                .execute();
        ret = response.getBody();
        retCode = JsonPath.read(ret, "$.retCode");
        assertEquals(retCode, 10000);
        System.out.println(ret);
    }
}
