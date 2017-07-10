package czrbt.lzy.mylibrary.utils;
// @author: lzy  time: 2016/09/29.


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseUtil {


    /**
     * list 转String数组
     *
     * @param list list
     * @return String[]
     */
    public static String[] ListTOStrings(List list) {
        if (list == null)
            return new String[0];
        String[] ss = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ss[i] = list.get(i).toString();
        }
        return ss;
    }

    public static final int N_FLOAT = 0, NC = 1, NCW = 2, N_PHONE = 3, CW = 4;
    /**
     1 [正则表达式]文本框输入内容控制
     2 整数或者小数：^[0-9]+\.{0,1}[0-9]{0,2}$
     3 只能输入数字："^[0-9]*$"。
     4 只能输入n位的数字："^\d{n}$"。
     5 只能输入至少n位的数字："^\d{n,}$"。
     6 只能输入m~n位的数字：。"^\d{m,n}$"
     7 只能输入零和非零开头的数字："^(0|[1-9][0-9]*)$"。
     8 只能输入有两位小数的正实数："^[0-9]+(.[0-9]{2})?$"。
     9 只能输入有1~3位小数的正实数："^[0-9]+(.[0-9]{1,3})?$"。
     10 只能输入非零的正整数："^\+?[1-9][0-9]*$"。
     11 只能输入非零的负整数："^\-[1-9][]0-9"*$。
     12 只能输入长度为3的字符："^.{3}$"。
     13 只能输入由26个英文字母组成的字符串："^[A-Za-z]+$"。
     14 只能输入由26个大写英文字母组成的字符串："^[A-Z]+$"。
     15 只能输入由26个小写英文字母组成的字符串："^[a-z]+$"。
     16 只能输入由数字和26个英文字母组成的字符串："^[A-Za-z0-9]+$"。
     17 只能输入由数字、26个英文字母或者下划线组成的字符串："^\w+$"。
     18 验证用户密码："^[a-zA-Z]\w{5,17}$"正确格式为：以字母开头，长度在6~18之间，只能包含字符、数字和下划线。
     19 验证是否含有^%&',;=?$\"等字符："[^%&',;=?$\x22]+"。
     20 只能输入汉字："^[\u4e00-\u9fa5]{0,}$"
     21 验证Email地址："^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$"。
     22 验证InternetURL："^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$"。
     23 验证电话号码："^(\(\d{3,4}-)|\d{3.4}-)?\d{7,8}$"正确格式为："XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX"。
     24 验证身份证号（15位或18位数字）："^\d{15}|\d{18}$"。
     25 验证一年的12个月："^(0?[1-9]|1[0-2])$"正确格式为："01"～"09"和"10"～"12"。
     26 验证一个月的31天："^((0?[1-9])|((1|2)[0-9])|30|31)$"正确格式为；"01"～"09"和"10"～"31"。
     27 匹配中文字符的正则表达式： [\u4e00-\u9fa5]
     28 匹配双字节字符(包括汉字在内)：[^\x00-\xff]
     29 应用：计算字符串的长度（一个双字节字符长度计2，ASCII字符计1）
     30 String.prototype.len=function(){return this.replace(/[^\x00-\xff]/g,"aa").length;}
     31 匹配空行的正则表达式：\n[\s| ]*\r
     32 匹配html标签的正则表达式：<(.*)>(.*)<\/(.*)>|<(.*)\/>
     33 匹配首尾空格的正则表达式：(^\s*)|(\s*$)
     */
    /**
     * 判断字符串是否合法
     *
     * @param str  字符串
     * @param type 类型
     * @return result
     */
    public static Boolean StrJudge(String str, int type) {
        String reg = "^[0-9a-zA-Z]$";
        switch (type) {
            case N_FLOAT:
                if (str.contains("."))
                    reg = "^[1-9]{1}+[0-9]{0,7}+\\.{1}[0-9]{0,2}$";//小数
                else
                    reg = "^[1-9]{1}+[0-9]{0,7}$";//整数
                break;
            case NC:
                reg = "^[0-9a-zA-Z]+$";//数字和字母
                break;
            case NCW:
                reg = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";//数字和字母和汉字
                break;
            case CW:
                reg = "^[a-zA-Z\u4e00-\u9fa5]+$";//字母和汉字
                break;
            case N_PHONE:
                reg = "^1[3|4|5|8]\\d{9}$";
                break;
        }

        return str.matches(reg);
    }


    /**
     * 隐藏键盘
     *
     * @param context xxx
     * @param v 对应 view
     */
    public static void HideSoftKey(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }
    /**后缀名，MIME类型*/
    public static Map<String, String> MIME_Map = new HashMap<>();

    static {
        MIME_Map.put(".3gp", "video/3gpp");
        MIME_Map.put(".apk", "application/vnd.android.package-archive");
        MIME_Map.put(".asf", "video/x-ms-asf");
        MIME_Map.put(".avi", "video/x-msvideo");
        MIME_Map.put(".bin", "application/octet-stream");
        MIME_Map.put(".bmp", "image/bmp");
        MIME_Map.put(".c", "text/plain");
        MIME_Map.put(".class", "application/octet-stream");
        MIME_Map.put(".conf", "text/plain");
        MIME_Map.put(".cpp", "text/plain");
        MIME_Map.put(".doc", "application/msword");
        MIME_Map.put(".docx",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        MIME_Map.put(".xls", "application/vnd.ms-excel");
        MIME_Map.put(".xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MIME_Map.put(".exe", "application/octet-stream");
        MIME_Map.put(".gif", "image/gif");
        MIME_Map.put(".gtar", "application/x-gtar");
        MIME_Map.put(".gz", "application/x-gzip");
        MIME_Map.put(".h", "text/plain");
        MIME_Map.put(".htm", "text/html");
        MIME_Map.put(".html", "text/html");
        MIME_Map.put(".jar", "application/java-archive");
        MIME_Map.put(".java", "text/plain");
        MIME_Map.put(".jpeg", "image/jpeg");
        MIME_Map.put(".jpg", "image/jpeg");
        MIME_Map.put(".js", "application/x-javascript");
        MIME_Map.put(".log", "text/plain");
        MIME_Map.put(".m3u", "audio/x-mpegurl");
        MIME_Map.put(".m4a", "audio/mp4a-latm");
        MIME_Map.put(".m4b", "audio/mp4a-latm");
        MIME_Map.put(".m4p", "audio/mp4a-latm");
        MIME_Map.put(".m4u", "video/vnd.mpegurl");
        MIME_Map.put(".m4v", "video/x-m4v");
        MIME_Map.put(".mov", "video/quicktime");
        MIME_Map.put(".mp2", "audio/x-mpeg");
        MIME_Map.put(".mp3", "audio/x-mpeg");
        MIME_Map.put(".mp4", "video/mp4");
        MIME_Map.put(".mpc", "application/vnd.mpohun.certificate");
        MIME_Map.put(".mpe", "video/mpeg");
        MIME_Map.put(".mpeg", "video/mpeg");
        MIME_Map.put(".mpg", "video/mpeg");
        MIME_Map.put(".mpg4", "video/mp4");
        MIME_Map.put(".mpga", "audio/mpeg");
        MIME_Map.put(".msg", "application/vnd.ms-outlook");
        MIME_Map.put(".ogg", "audio/ogg");
        MIME_Map.put(".pdf", "application/pdf");
        MIME_Map.put(".png", "image/png");
        MIME_Map.put(".pps", "application/vnd.ms-powerpoint");
        MIME_Map.put(".ppt", "application/vnd.ms-powerpoint");
        MIME_Map.put(".pptx",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        MIME_Map.put(".prop", "text/plain");
        MIME_Map.put(".rc", "text/plain");
        MIME_Map.put(".rmvb", "audio/x-pn-realaudio");
        MIME_Map.put(".rtf", "application/rtf");
        MIME_Map.put(".sh", "text/plain");
        MIME_Map.put(".tar", "application/x-tar");
        MIME_Map.put(".tgz", "application/x-compressed");
        MIME_Map.put(".txt", "text/plain");
        MIME_Map.put(".wav", "audio/x-wav");
        MIME_Map.put(".wma", "audio/x-ms-wma");
        MIME_Map.put(".wmv", "audio/x-ms-wmv");
        MIME_Map.put(".wps", "application/vnd.ms-works");
        MIME_Map.put(".xml", "text/plain");
        MIME_Map.put(".z", "application/x-compress");
        MIME_Map.put(".zip", "application/x-zip-compressed");
        MIME_Map.put("", "*/*");
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     * @param file 文件
     */
    public static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
		/* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end.equals(""))
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        return MIME_Map.get(end);
    }




}
