package com.yihuo.bihai.captcha.sdk.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient4Utils {
    private static final Logger log = LoggerFactory.getLogger(HttpClient4Utils.class);
    private static HttpClient defaultClient = createHttpClient(20, 20, 5000, 5000, 3000);

    public HttpClient4Utils() {
    }

    public static HttpClient createHttpClient(int maxTotal, int maxPerRoute, int socketTimeout, int connectTimeout, int connectionRequestTimeout) {
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(maxPerRoute);
        cm.setValidateAfterInactivity(200);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setConnectionTimeToLive(30L, TimeUnit.SECONDS).setRetryHandler(new StandardHttpRequestRetryHandler(3, true)).setDefaultRequestConfig(defaultRequestConfig).build();
        startMonitorThread(cm);
        return httpClient;
    }

    private static void startMonitorThread(final PoolingHttpClientConnectionManager cm) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        cm.closeExpiredConnections();
                        cm.closeIdleConnections(30L, TimeUnit.SECONDS);
                        TimeUnit.SECONDS.sleep(10L);
                    } catch (Exception var2) {
                        ;
                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public static String sendPost(HttpClient httpClient, String url, Map<String, String> params, Charset encoding) {
        String resp = "";
        HttpPost httpPost = new HttpPost(url);
        if (params != null && params.size() > 0) {
            List<NameValuePair> formParams = new ArrayList();
            Iterator itr = params.entrySet().iterator();

            while(itr.hasNext()) {
                Entry<String, String> entry = (Entry)itr.next();
                formParams.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
            }

            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formParams, encoding);
            httpPost.setEntity(postEntity);
        }

        CloseableHttpResponse response = null;

        try {
            response = (CloseableHttpResponse)httpClient.execute(httpPost);
            resp = EntityUtils.toString(response.getEntity(), encoding);
        } catch (Exception var17) {
            log.error("request url:" + url + " failed ", var17);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException var16) {
                    log.error("close response failed ", var16);
                }
            }

        }

        return resp;
    }

    public static String sendPost(String url, Map<String, String> params) {
        Charset encoding = Charset.forName("utf8");
        return sendPost(defaultClient, url, params, encoding);
    }
}
