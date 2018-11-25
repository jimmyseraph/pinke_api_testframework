package com.pinke.liudao.test;

import com.pinke.liudao.variable_pool.VariableWorker;
import org.testng.annotations.Test;

public class VariablePoolTest {
    @Test
    public void testVariable(){
        VariableWorker.put("name","liudao");
        VariableWorker.put("age","18");
        String s = "hahaha, my name is ${name}, i am ${age} years old";
        System.out.println(VariableWorker.parse(s));
    }
}
