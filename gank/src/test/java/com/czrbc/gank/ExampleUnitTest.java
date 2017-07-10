package com.czrbc.gank;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String 原来汉字还可以这么玩 = "略略略……^u^";

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public static void 开心(){
        System.out.println("你开心就好");
    }
    @Test
    public void testCn(){
        String 福利 = "hello,world";
        ExampleUnitTest.开心();
        System.out.println(福利);
        System.out.println(原来汉字还可以这么玩);
    }
}