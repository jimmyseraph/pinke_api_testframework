package com.pinke.liudao.core.okhttp;

import com.pinke.liudao.core.EasyRequest;
import com.pinke.liudao.core.EasyResponse;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用OkHttp实现EasyRequest
 * @author 六道先生
 * @version v1.0
 */
public class OkHttpEasyRequest implements EasyRequest {
    private final OkHttpClient client = new OkHttpClient();
    private String url;
    private String method;
    private Map<String, String> queryParams;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private MediaType mediaType;
    private RequestBody body;

    public OkHttpEasyRequest(){
        this.method = "GET";
        queryParams = new HashMap<>();
        headers = new HashMap<>();
        cookies = new HashMap<>();
        mediaType = MediaType.parse(EasyRequest.MEDIA_TYPE_PLAIN);
        body = null;
    }

    /**
     * 设置请求的url
     *
     * @param url 请求的url，可以带参数，也可以不带参数，而使用{@link #addQueryParam(String, String)}来添加参数
     * @return request对象本身
     */
    @Override
    public EasyRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 添加请求行的参数
     *
     * @param key   请求行参数的参数名
     * @param value 请求行参数的值
     * @return request对象本身
     */
    @Override
    public EasyRequest addQueryParam(String key, String value) {
        queryParams.put(key, value);
        return this;
    }

    /**
     * 设置请求的方法
     *
     * @param method 请求的方法，支持所有的http协议请求，忽略大小写
     * @return request对象本身
     */
    @Override
    public EasyRequest setMethod(String method) {
        this.method = method.toUpperCase();
        return this;
    }

    /**
     * 添加请求的header
     *
     * @param headerName  header的名称
     * @param headerValue header的值
     * @return request对象本身
     */
    @Override
    public EasyRequest addHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
        return this;
    }

    /**
     * 添加请求的cookie
     *
     * @param cookieName  cookie的名字
     * @param cookieValue cookie的值
     * @return request对象本身
     */
    @Override
    public EasyRequest addCookie(String cookieName, String cookieValue) {
        cookies.put(cookieName, cookieValue);
        return this;
    }

    /**
     * 设置请求的body部分
     *
     * @param mediaType body部分的类型（application/json）
     * @param content   body的内容
     * @return request对象本身
     */
    @Override
    public EasyRequest setBody(String mediaType, String content) {
        this.mediaType = MediaType.parse(mediaType);
        this.body = RequestBody.create(this.mediaType, content);
        return this;
    }

    /**
     * 执行该请求对象
     *
     * @return 返回值
     * @throws IOException 请求出现异常
     */
    @Override
    public EasyResponse execute() throws IOException {
        Request.Builder builder = new Request.Builder();
        // 1、拼装url
        // 1.1、检查用户是否给queryParams添加了值
        if(queryParams.size() > 0){
            // 1.2 将map类型的数据拼接成“&key1=value1&key2=value2&key3=value3.....”这样的格式
            StringBuffer queryStr = new StringBuffer();
            queryParams.forEach((key, value) ->
                queryStr.append("&")
                    .append(key)
                    .append("=")
                    .append(value)
            );
            // 1.3 检查用户是否在url地址上已经写过参数了
            if(!this.url.contains("?")){
                queryStr.replace(0,1,"?");
            }
            // 1.4 正式将url和query string拼接
            this.url = this.url + queryStr;
        }
        builder.url(this.url); // 将url传递给okhttp

        // 2、检查用户是否添加了header
        if(headers.size() > 0){
            headers.forEach(builder::addHeader);
        }
        // 3、检查用户是否添加了cookie
        if(cookies.size() > 0){
            // 等价于添加一个名叫Cookie的header，值是 key1=value1; key2=value2.....
            StringBuffer cookieStr = new StringBuffer();
            cookies.forEach((key, value) ->
                cookieStr.append(key)
                    .append("=")
                    .append(value)
                    .append(";")
            );
            builder.addHeader("Cookie",cookieStr.toString());
        }
        // 4、将body、method传给okhttp
        builder.method(this.method, this.body);
        // 5、构建okhttp请求，并获得响应
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        return new OkHttpEasyResponse(response);
    }
}
