package com.pinke.liudao.core.okhttp;

import com.pinke.liudao.core.EasyResponse;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

/**
 * 使用OkHttp实现EasyResponse
 * @author 六道先生
 * @version v1.0
 */
public class OkHttpEasyResponse implements EasyResponse {

    private Response response;
    private String bodyStr;

    public OkHttpEasyResponse(Response response) throws IOException {
        this.response = response;
        bodyStr = response.body().string();
    }

    /**
     * 获取响应的code
     *
     * @return code(200 ， 302 ， 404 ....)
     */
    @Override
    public int getCode() {
        return response.code();
    }

    /**
     * 获取响应中指定的header
     *
     * @param headerName 希望获取的header的名字
     * @return header的值
     */
    @Override
    public String getHeader(String headerName) {
        return response.header(headerName);
    }

    /**
     * 获取响应中指定的cookie
     *
     * @param cookieName cookie的名字
     * @return cookie的值
     */
    @Override
    public String getCookie(String cookieName) {
        List<String> cookies = response.headers("Set-Cookie");
        for(int i = 0; i < cookies.size(); i++){
            String cookieKeyValue = cookies.get(i).split(";")[0];
            String[] keyValuePair = cookieKeyValue.split("=");
            if(keyValuePair[0].trim().equals(cookieName)){
                return keyValuePair[1];
            }
        }
        return null;
    }

    /**
     * 获取响应的body字符串
     *
     * @return body字符串
     */
    @Override
    public String getBody() {
        return bodyStr;
    }
}
