package com.zhxu.ssm.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

public class HttpRequestUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static JSONObject httpPost(String url, List<NameValuePair> params, boolean noNeedResponse) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonResult = null;
        try {
            if (null != params) {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
            }
            response = httpClient.execute(httpPost);
            url = URLDecoder.decode(url, "UTF-8");
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    str = EntityUtils.toString(response.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    jsonResult = JSON.parseObject(str);
                } catch (IOException e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            close(httpClient, response);
        }
        return jsonResult;
    }

    public static JSONObject httpGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        JSONObject jsonResult = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                jsonResult = JSON.parseObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        } finally {
            close(httpClient, response);
        }
        return jsonResult;
    }

    private static void close(CloseableHttpClient httpClient, CloseableHttpResponse response) {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (response != null) {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
