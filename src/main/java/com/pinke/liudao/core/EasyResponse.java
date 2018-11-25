package com.pinke.liudao.core;

/**
 * EasyAPI框架的核心接口，用于处理所有和响应相关的内容
 * @author 六道先生
 * @version v1.0
 *
 * <p>
 *     response接口需要处理：
 *     1、能够获取response code
 *     2、能够获取header
 *     3、能够获取cookie
 *     4、能够获取body
 * </p>
 */
public interface EasyResponse {

    /**
     * 获取响应的code
     * @return code(200，302，404 ....)
     */
    int getCode();

    /**
     * 获取响应中指定的header
     * @param headerName 希望获取的header的名字
     * @return header的值
     */
    String getHeader(String headerName);

    /**
     * 获取响应中指定的cookie
     * @param cookieName cookie的名字
     * @return cookie的值
     */
    String getCookie(String cookieName);

    /**
     * 获取响应的body字符串
     * @return body字符串
     */
    String getBody();
}
