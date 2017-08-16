package com.zhxu.ssm.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <B>功能简述</B><br>
 * URL生成工具对应的工具类
 *
 * @author lhh
 * @date 2017/7/20 下午4:03
 * @return
 */
public class UrlUtils {
    /**
     *
     * <B>功能简述</B><br>
     * 拼接auth_token_hash相关url
     *
     * @param userName
     * @param auth_token_hash
     * @date 2017/7/27 下午7:05
     * @author lhh
     * @return java.lang.String
     */
    public static JSONObject getHttpUrl(String userName , String auth_token_hash){
        String url="http://sdk-lb-dyrs-1198084236.cn-north-1.elb.amazonaws.com.cn:8081/v1/get_token?";
        StringBuffer sb = new StringBuffer(url);
        sb.append("user_name="+userName).append("&auth_token_hash="+String.valueOf(auth_token_hash));
        return  HttpGetToken.httpGet(sb.toString());
    }

    /**
     *
     * <B>功能简述</B><br>
     * 返回对应的map
     *
     * @param Url
     * @date 2017/7/21 上午10:11
     * @author lhh
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String, Object> getUrl(String Url) {
        Map<String, Object> map = new HashMap<>();
//        if (!Url.contains(".")){
//            map=onlyOne(Url);
//            return map;
//        }
        if (judgeUrl(Url)) {
            Url = Url.trim().toLowerCase();
            if (Url.lastIndexOf("https://") == 0) {
                Url = Url.substring(8);
            } else {
                if (Url.lastIndexOf("http://") == 0) {
                    Url = Url.substring(7);
                } else {
                    map.put("wrong", false);
                    return map;
                }
            }
            //把后面的那部分进行详细的处理
            map = containcim(Url);
            return map;
        }
        map.put("wrong", false);
        return map;
    }

    /**
     *
     * <B>功能简述</B><br>
     * url的进一步拼接和逻辑处理
     *
     * @param url
     * @date 2017/7/21 上午10:12
     * @author lhh
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private static Map<String, Object> onlyOne(String url) {
        Map<String, Object> map = new HashMap<>();
        //四种情况
        String baidu = "http://www." + url + ".com.cn?uc_sr=baidu&uc_abtest={abtest}&uc_adposition={adposition}&uc_bidurl" +
                "={bidurl}&uc_creative={creative}&uc_dongtai={dongtai}&uc_haoci={haoci}&uc_keywordid={keywordid}" +
                "&uc_matchtype={matchtype}&uc_mediatype={mediatype}&uc_pagenum={pagenum}&uc_placement={placement}";
        String shenma = "http://www." + url + ".com.cn?uc_sr=sm&uc_keywordid={keywordid}&uc_creative={creative}&uc_deviceid={deviceid}";
        String sougou = "http://www." + url + ".com.cn?uc_sr=sogou&uc_keywordid={keywordid}";
        String san = "http://www." + url + ".com.cn?&uc_sr=360&uc_userid={userid}&uc_planid={planid}&uc_groupid={groupid}&uc_creativeid=" +
                "{creativeid}&uc_wordid={wordid}&uc_device={device}";
        map.put("baidu", baidu);
        map.put("shenma", shenma);
        map.put("sougou", sougou);
        map.put("san", san);
        return map;
    }

    private static Map<String, Object> onlyTwo(String url) {
        Map<String, Object> map = new HashMap<>();
        //四种情况
        String baidu = "http://www." + url + "uc_sr=baidu&uc_abtest={abtest}&uc_adposition={adposition}&uc_bidurl" +
                "={bidurl}&uc_creative={creative}&uc_dongtai={dongtai}&uc_haoci={haoci}&uc_keywordid={keywordid}" +
                "&uc_matchtype={matchtype}&uc_mediatype={mediatype}&uc_pagenum={pagenum}&uc_placement={placement}";
        String shenma = "http://www." + url + "uc_sr=sm&uc_keywordid={keywordid}&uc_creative={creative}&uc_deviceid={deviceid}";
        String sougou = "http://www." + url + "uc_sr=sogou&uc_keywordid={keywordid}";
        String san = "http://www." + url + "uc_sr=360&uc_userid={userid}&uc_planid={planid}&uc_groupid={groupid}&uc_creativeid=" +
                "{creativeid}&uc_wordid={wordid}&uc_device={device}";
        map.put("baidu", baidu);
        map.put("shenma", shenma);
        map.put("sougou", sougou);
        map.put("san", san);
        return map;
    }


    public static int containsOf(String s) {
        return s.length() - s.replace("?", "").length();
    }
    /**
     *
     * <B>功能简述</B><br>
     * 判断包含了几个 特殊符号
     *
     * @param
     * @date 2017/7/21 上午10:15
     * @author lhh
     * @return int
     */
    public static int containsOfe(String s) {
        return s.length() - s.replace("=", "").length();
    }

    private static Map<String, Object> containcim(String sb) {
        Map<String, Object> map = new HashMap<>();
        String url = "";
        if (sb.lastIndexOf("w.") <= 4) {
            sb = sb.substring(sb.lastIndexOf("w.") + 2);
            if (containsOf(sb) <= 1) {
                if (containsOf(sb) == 0 && sb.contains("=")) {
                    map.put("wrong", false);
                    return map;
                } else if (containsOf(sb) == 0 && !sb.contains("=")) {
                    return onlyTwo((sb + "?"));
                } else if (containsOf(sb) == 1 && !sb.contains("=")) {
                    return onlyTwo(sb);
                } else if (containsOf(sb) == 1 && sb.contains("=")) {
                    if (sb.endsWith("&")) {
                        return onlyTwo(sb);
                    }
                    return onlyTwo((sb + "&"));
                } else {
                    map.put("wrong", false);
                    return map;
                }
            } else {
                map.put("wrong", false);
                return map;
            }
        }
//        map.put("wrong", false);
        return map;

    }

    /**
     * 此正则:1_必须以 http:// 或者 https:// 开头
     * 2_中间长度 无限定
     * 3_必须以.net|com|cn|org|cc|tv 其中一个结尾
     */
    private static Boolean judgeUrl(String Url) {
        Pattern p = Pattern.compile("^((http|https)://)(([a-zA-Z0-9]+[.])+(net|com|cn|org|cc|tv{1,3}))");
        Matcher m = p.matcher(Url);
        return m.find();
    }

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *               <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *               <p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[3458]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        String regex = "\\-?[1-9]\\d+";
        return Pattern.matches(regex, digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex, decimals);
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) {
        String regex = "\\s+";
        return Pattern.matches(regex, blankSpace);
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex, chinese);
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex, birthday);
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }
}
