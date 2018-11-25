package com.pinke.liudao.variable_pool;

import java.util.HashMap;
import java.util.Map;

/**
 * 变量工作者，负责保存用户指定的变量，并可随时取出，变量是线程安全的，每个独立的线程不共享变量
 * @author 六道先生
 * @version v1.0
 */
public class VariableWorker {
    /**
     * 变量池
     */
    private static final ThreadLocal<Map<String, String>> threadVariable = ThreadLocal.withInitial(VariableWorker::init);

    /**
     * 定义默认的变量识别的左边符号
     */
    private static String VARIABLE_BRACE_LEFT = "${";

    /**
     * 定义默认的变量识别的右边符号
     */
    private static String VARIABLE_BRACE_RIGHT = "}";

    /**
     * 初始化变量池
     * @return 空的变量池
     */
    private static Map<String, String> init(){
        Map<String, String> variableMap = new HashMap<>();
        return variableMap;
    }

    /**
     * 将变量放入变量池
     * @param name 变量名
     * @param value 变量的值
     */
    public static void put(String name, String value){
        threadVariable.get().put(name, value);
    }

    /**
     * 从变量池中取出指定名称的变量
     * @param name 变量名
     * @return 变量的值
     */
    public static String get(String name){
        String value = threadVariable.get().get(name);
        return value == null ? "" : value;
    }

    /**
     * 清空整个变量池
     */
    public static void clearAll(){
        threadVariable.get().clear();
    }

    /**
     * 解析含有变量的字符串，将变量替换为变量的值后返回
     * @param content 含有变量的字符串
     * @return 替换后的字符串
     */
    public static String parse(String content){
        String[] processedStrs = null;
        for(
                processedStrs = extractVariable(content);
                processedStrs[1] != null;
                processedStrs = extractVariable(content)
        ){
            content = processedStrs[0] + get(processedStrs[1]) + processedStrs[2];
        }
        return content;
    }

    /**
     * 将含有变量的字符串拆开，第一部分是变量左边的字符串，第二部分是变量名称，第三部分是变量右边的字符串
     * @param content 含有变量的字符串
     * @return 拆成三部分的字符串数组
     */
    private static String[] extractVariable(String content){
        String[] extractStr = new String[3];
        int pre_index = content.indexOf(VARIABLE_BRACE_LEFT);
        if(pre_index == -1){
            extractStr[0] = content;
            return extractStr;
        }
        String temp = content.substring(pre_index + VARIABLE_BRACE_LEFT.length());
        int suf_index = temp.indexOf(VARIABLE_BRACE_RIGHT);
        if(suf_index == -1){
            extractStr[0] = content;
            return extractStr;
        }
        extractStr[0] = content.substring(0, pre_index);
        extractStr[1] = temp.substring(0, suf_index);
        extractStr[2] = temp.substring(suf_index + VARIABLE_BRACE_RIGHT.length());
        return extractStr;
    }
}
