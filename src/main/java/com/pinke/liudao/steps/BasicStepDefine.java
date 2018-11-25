package com.pinke.liudao.steps;

import com.jayway.jsonpath.JsonPath;
import com.pinke.liudao.core.EasyRequest;
import com.pinke.liudao.core.EasyResponse;
import com.pinke.liudao.core.okhttp.OkHttpEasyRequest;
import com.pinke.liudao.variable_pool.VariableWorker;
import cucumber.api.java.en.When;
import static org.testng.Assert.*;
import java.io.IOException;

/**
 * 定义常用句子
 * @author 六道先生
 * @version v1.0
 *
 * <p>
 *     常用句子：
 *     1、请求地址为http://xx.xx.xx/xx
 *     2、请求方法为POST
 *     3、添加请求行参数名为pageSize，值为10
 *     4、添加请求头名为Access-Token，值为abcde
 *     5、添加cookie名为xx，值为xx
 *     6、body部分类型为xx，值为xxx
 *     7、发起请求
 *     8、从响应的body中提取$.data.token，保存到变量token中
 *     9、检查响应码值为200
 *     10、检查响应的body中$.data.xx的值为xxxx
 *     11、打印body字符串
 * </p>
 */
public class BasicStepDefine {
    private EasyRequest request = new OkHttpEasyRequest();
    private EasyResponse response;

    @When("^请求地址为(.*?)$")
    public void set_url(String url){
        url = VariableWorker.parse(url);
        request.setUrl(url);
    }

    @When("^请求方法为([a-zA-Z]*?)$")
    public void set_method(String method){
        request.setMethod(method);
    }

    @When("^添加请求行参数名为(.*?)，值为(.*?)$")
    public void add_query_param(String name, String value){
        value = VariableWorker.parse(value);
        request.addQueryParam(name, value);
    }

    @When("^添加请求头名为(.*?)，值为(.*?)$")
    public void add_header(String name, String value){
        value = VariableWorker.parse(value);
        request.addHeader(name, value);
    }

    @When("^添加cookie名为(.*?)，值为(.*?)$")
    public void add_cookie(String name, String value){
        value = VariableWorker.parse(value);
        request.addCookie(name, value);
    }

    @When("^body部分类型为(.*?)，值为$")
    public void set_body(String mediaType, String content){
        if(mediaType.equalsIgnoreCase("form")){
            mediaType = EasyRequest.MEDIA_TYPE_FORM_DATA;
        }else if(mediaType.equalsIgnoreCase("json")){
            mediaType = EasyRequest.MEDIA_TYPE_JSON;
        }else if(mediaType.equalsIgnoreCase("xml")){
            mediaType = EasyRequest.MEDIA_TYPE_XML;
        }else if(mediaType.equalsIgnoreCase("soap12")){
            mediaType = EasyRequest.MEDIA_TYPE_SOAP12;
        }else{
            mediaType = EasyRequest.MEDIA_TYPE_PLAIN;
        }
        content = VariableWorker.parse(content);
        request.setBody(mediaType, content);
    }

    @When("^发起请求$")
    public void execute() throws IOException {
        response = request.execute();
    }

    @When("^从响应的body中提取(.*?)，保存到变量(.*?)中$")
    public void json_extract_from_body(String path, String varName){
        String varValue = JsonPath.read(response.getBody(), path);
        VariableWorker.put(varName, varValue);
    }

    @When("^检查响应码值为(\\d{3})$")
    public void check_response_code(int code){
        assertEquals(response.getCode(), code);
    }

    @When("^检查响应的body中(.*?)的值为(.*?)$")
    public void check_body_json_value(String path, String value){
        String actual = JsonPath.read(response.getBody(), path);
        assertEquals(actual, value);
    }

    @When("^打印body字符串$")
    public void print_body(){
        System.out.println(response.getBody());
    }
}
