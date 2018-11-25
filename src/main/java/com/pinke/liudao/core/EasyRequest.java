package com.pinke.liudao.core;

import java.io.IOException;

/**
 * EasyAPI框架的核心接口，用于处理所有和请求相关的内容
 * @author 六道先生
 * @version v1.0
 *
 * <p>
 *     请求必须能处理的内容：
 *     1、能设置url;
 *     2、能够给url拼接query params;
 *     3、设置请求的方法（get\post\put...）;
 *     4、能够添加header;
 *     5、能够添加cookie;
 *     6、能够设置body部分;
 * </p>
 */
public interface EasyRequest {
    /**
     * Media type for plain
     */
    String MEDIA_TYPE_PLAIN = "text/plain; charset=utf-8";
    /**
     * Media type for form data
     */
    String MEDIA_TYPE_FORM_DATA = "application/x-www-form-urlencoded; charset=utf-8";
    /**
     * Media type for json
     */
    String MEDIA_TYPE_JSON = "application/json; charset=utf-8";
    /**
     * Media type for xml
     */
    String MEDIA_TYPE_XML = "application/xml; charset=utf-8";
    /**
     * Media type for soap 1.2
     */
    String MEDIA_TYPE_SOAP12 = "application/xml+soap; charset=utf-8";

    /**
     * 设置请求的url
     * @param url 请求的url，可以带参数，也可以不带参数，而使用{@link #addQueryParam(String, String)}来添加参数
     * @return request对象本身
     */
    EasyRequest setUrl(String url);

    /**
     * 添加请求行的参数
     * @param key 请求行参数的参数名
     * @param value 请求行参数的值
     * @return request对象本身
     */
    EasyRequest addQueryParam(String key, String value);

    /**
     * 设置请求的方法
     * @param method 请求的方法，支持所有的http协议请求，忽略大小写
     * @return request对象本身
     */
    EasyRequest setMethod(String method);

    /**
     * 添加请求的header
     * @param headerName header的名称
     * @param headerValue header的值
     * @return request对象本身
     */
    EasyRequest addHeader(String headerName, String headerValue);

    /**
     * 添加请求的cookie
     * @param cookieName cookie的名字
     * @param cookieValue cookie的值
     * @return request对象本身
     */
    EasyRequest addCookie(String cookieName, String cookieValue);

    /**
     * 设置请求的body部分
     * @param mediaType body部分的类型（application/json）
     * @param content body的内容
     * @return request对象本身
     */
    EasyRequest setBody(String mediaType, String content);

    /**
     * 执行该请求对象
     * @return 返回值
     * @throws IOException 请求出现异常
     */
    EasyResponse execute() throws IOException;
}
