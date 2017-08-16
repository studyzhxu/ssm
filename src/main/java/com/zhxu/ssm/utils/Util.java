package com.zhxu.ssm.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * <B>功能简述</B><br>
 * 工具类
 *
 * @author xusheng
 * @date 2015年8月28日 上午10:44:15
 * @since [CIM/基础工具类 v1.0]
 */
public class Util {

    private static final Logger log = LoggerFactory.getLogger(Util.class);

    /**
     * <B>功能简述</B><br>
     * 获取配置文件key参数
     *
     * @param fileName 配置文件名
     * @param key      配置文件中的键
     * @return
     * @date 2015年5月23日 上午10:18:56
     * @author XuSheng
     */
    public static String getValueByKey(final String fileName, final String key) {
        final PropertyResourceBundle pb = (PropertyResourceBundle) ResourceBundle.getBundle(fileName);
        return pb.getString(key);
    }

    /**
     * <B>功能简述</B><br>
     * 参数处理
     *
     * @param request
     * @return
     * @date 2015年5月22日 下午11:23:26
     * @author XuSheng
     */
    public static Map<String, Object> reqParamToGenericMap(HttpServletRequest request) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        Enumeration<?> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            final String name = (String) names.nextElement();
            newMap.put(name, getArraySubset(request.getParameterValues(name)));
        }
        return newMap;
    }

    private static String getArraySubset(String[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    /**
     * <B>功能简述</B><br>
     * 随机生成字符串sum 位字符串
     *
     * @param sum 任意数字
     * @return
     * @date 2015年5月22日 下午4:35:11
     * @author XuSheng
     */
    public static String getRandomStr(final int sum) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sum; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * <B>功能简述</B><br>
     * 随机生成字符串sum 位数字 首位数字不为0
     *
     * @param sum 任意数字
     * @return
     * @date 2015年5月22日 下午4:35:11
     * @author XuSheng
     */
    public static String getRandomInt(final int sum) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        String oneBase = "123456789";
        for (int i = 0; i < 1; i++) {
            int number = random.nextInt(oneBase.length());
            sb.append(oneBase.charAt(number));
        }
        String base = "0123456789";
        for (int i = 0; i < sum - 1; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * <B>功能简述</B><br>
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     * @date 2015年5月22日 下午11:24:02
     * @author XuSheng
     */
    public static String getExtention(String fileName) {
        String fname = "";
        try {
            int pos = fileName.lastIndexOf(".");
            fname = fileName.substring(pos);
        } catch (Exception e) {
            fname = ".jpg";
            log.error("get extention is error : ", e);
        }
        return fname;
    }

    /**
     * <B>功能简述</B><br>
     * 检查是否缺少参数
     *
     * @param params
     * @param strs   参数数组
     * @return true 缺少| false 不缺少
     * @date 2015年5月24日 上午1:47:52
     * @author XuSheng
     */
    public static boolean checkParams(final Map<String, Object> params, String... strs) {
        for (String s : strs) {
            if (null == params.get(s)) {
                if (log.isDebugEnabled()) {
                    log.debug("missing param is  : {}", s);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * <B>功能简述</B><br>
     * 检查是否缺少参数或者为空字符串
     *
     * @param params
     * @param strs   参数数组
     * @return true 缺少| false 不缺少
     * @date 2015年5月24日 上午1:47:52
     * @author XuSheng
     */
    public static boolean checkEmptyStr(final Map<String, Object> params, String... strs) {
        for (String s : strs) {
            if (null == params.get(s) || params.get(s).equals("")) {
                if (log.isDebugEnabled()) {
                    log.debug("missing or empty param is  : {}", s);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * <B>功能简述</B><br>
     * 检查是否缺少参数或者为空字符串
     */
    public static boolean checkEmptyStr(Object params) {
        if (null == params || String.valueOf(params).equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <B>功能简述</B><br>
     * 获取下个用户编号 编号随机20-50之间
     *
     * @param userSeiralNumber
     * @return
     * @date 2015年11月30日 上午11:51:04
     * @author xusheng
     */
    public synchronized static String getNextUserSeiralNumber(String userSeiralNumber) {
        Random random = new Random();
        double d1 = 20 + random.nextDouble() * 30;
        return String.valueOf(new BigDecimal(Integer.valueOf(userSeiralNumber) + (int) d1).toPlainString());
    }

    /**
     * <B>功能简述</B><br>
     * 获取客户端IP地址
     *
     * @param request
     * @return
     * @date 2015年12月4日 下午1:05:31
     * @author xusheng
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (null == request) {
            return "";
        }
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Forwarded-For");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 格式化金额
     *
     * @param amount   需要格式化的金额
     * @param df       格式
     * @param currency 货币单位
     * @return 返回格式化后的金额字符串 如：$120.00 或 ¥120.00
     */
    public static String formatAmount(BigDecimal amount, DecimalFormat df, String currency) {
        Map<String, String> currencyMap = new HashMap<>();
        currencyMap.put("USD", "$");
        currencyMap.put("RMB", "¥");

        String fmAmount;
        if (amount == null) {
            fmAmount = String.format("%s%s", currencyMap.get(currency) == null ? "" : currencyMap.get(currency), "0.00");
        } else {
            fmAmount = String.format("%s%s", currencyMap.get(currency) == null ? "" : currencyMap.get(currency), df.format(amount));
        }
        return fmAmount;
    }

    /**
     * 格式化百分比
     *
     * @param percent         需要格式化的百分比
     * @param df              格式
     * @param percentLevel    百分比（100）或者千分比（1000）
     * @param withPercentChar 是否添加%／‰
     * @return 返回格式化后的百分比字符串 如：12.00% 或 12.00‰
     */
    public static String formatPercent(BigDecimal percent, DecimalFormat df, int percentLevel, boolean withPercentChar) {
        String percentChar = "%";
        if (1000 == percentLevel) {
            percentChar = "‰";
        }
        if (!withPercentChar) {
            percentChar = "";
        }

        String fmPercent;
        if (percent == null) {
            fmPercent = String.format("%s%s", "0.00", percentChar);
        } else {
            fmPercent = String.format("%s%s", df.format(percent.multiply(new BigDecimal(percentLevel))), percentChar);
        }

        return fmPercent;
    }

    /**
     * 活动默认的统计粒度
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 统计粒度 "HOURLY, DAILY, WEEKLY, MONTHLY"
     */
    public static String getDefaultGranularity(DateTime startTime, DateTime endTime, String reportType) {
        Period p = new Period(startTime, endTime, PeriodType.days());
        int day = p.getDays();
        String granularity = "DAILY";
        if (day <= 7) {
            if (reportType.equals("date") || reportType.equals("adgroup")) {
                granularity = "HOURLY";
            }
        } else if (day > 7 && day < 14) {
            granularity = "DAILY";
        } else if (day >= 14 && day < 60) {
            granularity = "DAILY";
        } else if (day >= 60 && day < 90) {
            granularity = "DAILY";
        } else if (day >= 90) {
            granularity = "WEEKLY";
        }
        return granularity;
    }

    /**
     * <B>功能简述</B><br>
     * 获取HTTP请求的UA
     *
     * @param request
     * @return
     * @date 2017年1月9日 上午1:03:19
     * @author xusheng
     */
    public static String getUserAgent(HttpServletRequest request) {
        if (null == request) {
            return "";
        }
        String userAgent = request.getHeader("User-Agent");

        return userAgent;
    }

    /**
     * 将本地时间转成UTC 时间
     *
     * @param localTime 本地时间
     * @return UTC 时间
     */
    public static DateTime utcTime(DateTime localTime) {
        DateTime utc;
        utc = localTime.minusHours(8);// 统一使用UTC时间
        return utc;
    }

    /**
     * <B>功能简述</B><br>
     * 获取签到奖励
     *
     * @param daysParams 第几天奖励
     * @return
     * @date 2015年12月22日 下午1:31:16
     * @author xusheng
     */
    public static BigDecimal getCheckInReward(String daysParams) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("days params : {}", daysParams);
            }
            return new BigDecimal(getValueByKey("app", daysParams));
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 获取随机UUID
     *
     * @return
     * @author xusheng
     */
    public static String getUUIDStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String getIdfa(String osVersion, String idfa) {
        // idfa : IOS7及以上传原始IDFA，否则传空；必须批量排重，最多可以支持300条IDFA；
        if (osVersion != null) {
            String[] osv = osVersion.split(".");
            if (osv.length > 0) {
                if (Integer.parseInt(osv[0]) <= 6) {
                    idfa = "";
                }
            }
        }
        return idfa;
    }

    public static String getMac(String osVersion) {
        // mac : IOS6及以下传原始MAC，否则传02:00:00:00:00:00；
        String mac = "02:00:00:00:00:00";
        if (osVersion != null) {
            String[] osv = osVersion.split(".");
            if (osv.length > 0) {
                if (Integer.parseInt(osv[0]) <= 6) {
                    mac = "";
                } else {
                    mac = "02:00:00:00:00:00";
                }
            }
        }
        return mac;
    }

    public static boolean notAMI(int sourceId) {
        final int AMI_SOURCE = 1;
        if (sourceId != AMI_SOURCE) {
            return true;
        }
        return false;
    }

    public static DateTime toDateTime(Object createTime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        DateTime dateTime = null;
        try {
            dateTime = formatter.parseDateTime(String.valueOf(createTime));
        } catch (Exception e) {
            formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            dateTime = formatter.parseDateTime(String.valueOf(createTime));
        }
        return dateTime;
    }

    /**
     * <B>功能简述</B><br>
     * 时间、时区-->UTC时间
     *
     * @param date    当前时间
     * @param zoneStr 该时间的时区 Asia/Shanghai Asia/Hong_Kong Europe/London
     * @return UTC 时间字符串
     * @date 2016年12月27日 下午1:00:00
     * @author xusheng
     */
    public static Date dateConversionUTC(String date, String zoneStr) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ssZZZZ");
        String time = (date).concat(zoneStr);
        DateTime dt = formatter.parseDateTime(time);
        return new DateTime(dt, DateTimeZone.forID("UTC")).toDate();
    }

    /**
     * 获得粒度code
     *
     * @param granularity
     * @return
     */
    public static String getGranularityCode(String granularity) {
        if (null == granularity || "null".equals(granularity)) {
            granularity = "DAILY";
        }
        Map<String, String> gCode = new HashMap<>();
        gCode.put("时", "HOURLY");
        gCode.put("日", "DAILY");
        gCode.put("周", "WEEKLY");
        gCode.put("月", "MONTHLY");
        return gCode.get(granularity);
    }

    /**
     * 获得粒度显示名称
     *
     * @param granularityCode
     * @return
     */
    public static String getGranularityDisplayName(String granularityCode) {
        if (null == granularityCode || "null".equals(granularityCode)) {
            granularityCode = "日";
        }
        Map<String, String> gCode = new HashMap<>();
        gCode.put("HOURLY", "时");
        gCode.put("DAILY", "日");
        gCode.put("WEEKLY", "周");
        gCode.put("MONTHLY", "月");
        return gCode.get(granularityCode);
    }

    /**
     * <B>功能简述</B><br>
     * 读取文件
     *
     * @param fileName
     * @return
     * @date 2017年7月19日 下午3:55:37
     * @author xusheng
     */
    public static String read(String fileName) {
        File file = new File(fileName);
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                if (!lineTxt.contains("-----"))
                    sb.append(lineTxt).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * <B>功能简述</B><br>
     * 验证数据是否合法
     *
     * @param params
     * @return
     * @date 2017年07月20日 下午15:03:19
     * @author liuhongshen
     */
    public static boolean isLegalData(String... params) {
        if (params == null || params.length == 0) {
            return false;
        }
        for (String str : params) {
            if (str == null || str.length() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * <B>功能简述</B><br>
     * 隐藏部分字符串
     *
     * @param
     * @return
     * @date 2017/7/21 上午9:47
     * @author liuhongshen
     */
    public static String maskedStr(String str) {
        if (str == null) return null;
        int length = str.length();
        if (length < 3) return str;
        int count = str.length() - 2;
        StringBuilder sb = new StringBuilder(str.charAt(0) + "");
        for (int i = 1; i <= count; i++) {
            sb.append("*");
        }
        return sb.append(str.charAt(length - 1) + "").toString().intern();
    }

}
