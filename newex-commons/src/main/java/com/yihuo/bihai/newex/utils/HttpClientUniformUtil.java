package com.yihuo.bihai.newex.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.yihuo.bihai.newex.utils.uniform.AES;
import com.yihuo.bihai.newex.utils.uniform.SignUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:40
 */
@Component
public class HttpClientUniformUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUniformUtil.class);
    
    public static RestTemplate restTemplate;
    public static String AES_KEY;
    public static String SIGN_KEY;
    public static int referer;
    public static int siteId;

    private HttpClientUniformUtil() {
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headerMap, String charset) {
        return null != restTemplate ? postByRestTemplate(url, params, headerMap) : postByHttpClient(url, params, headerMap, charset);
    }

    private static String postByHttpClient(String url, Map<String, String> params, Map<String, String> headerMap, String charset) {
        HttpPost request = new HttpPost(url);
        if (params != null && params.size() > 0) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            List<NameValuePair> paramList = new ArrayList();
            Iterator var7 = entrySet.iterator();

            while(var7.hasNext()) {
                Entry<String, String> entry = (Entry)var7.next();
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                if (key != null && value != null) {
                    NameValuePair nvp = new BasicNameValuePair(key, value);
                    paramList.add(nvp);
                }
            }

            try {
                if (StringUtils.isEmpty(charset)) {
                    request.setEntity(new UrlEncodedFormEntity(paramList));
                } else {
                    request.setEntity(new UrlEncodedFormEntity(paramList, Charset.forName(charset)));
                }
            } catch (Exception var12) {
                LOGGER.error("HttpClientUtils post", var12);
                return null;
            }
        }

        return execute(request, headerMap);
    }

    private static String postByRestTemplate(String url, Map<String, String> params, Map<String, String> headerMap) {
        String urls = url;
        HttpHeaders headers;
        if (null != headerMap && headerMap.size() > 0) {
            headers = new HttpHeaders();
            headerMap.forEach((k, v) -> {
                headers.add(k, v);
            });
        } else {
            headers = null;
        }

        if (null != params && params.size() > 0) {
            if (url.contains("?")) {
                urls = url + "&";
            } else {
                urls = url + "?";
            }

            Entry e;
            for(Iterator var5 = params.entrySet().iterator(); var5.hasNext(); urls = urls + "&" + (String)e.getKey() + "={" + (String)e.getKey() + "}") {
                e = (Entry)var5.next();
            }
        }

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity((Object)null, headers);
        return (String)restTemplate.postForObject(urls, requestEntity, String.class, params);
    }

    private static String execute(HttpRequestBase request, Map<String, String> headerMap) {
        StringBuilder log = new StringBuilder("HttpClientUtils execute  method:" + request.getMethod() + " url:" + request.getURI());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        boolean isLog = false;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        String result = "";
        if (headerMap != null && headerMap.size() > 0) {
            Iterator var9 = headerMap.keySet().iterator();

            while(var9.hasNext()) {
                String key = (String)var9.next();
                request.setHeader(key, (String)headerMap.get(key));
            }
        }

        Object entity;
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
            request.setConfig(requestConfig);

            try {
                CloseableHttpResponse response = httpClient.execute(request);
                Throwable var11 = null;

                try {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        entity = response.getEntity();
                        if (entity != null) {
                            InputStream resStream = ((org.apache.http.HttpEntity)entity).getContent();

                            try {
                                try {
                                    inputStreamReader = new InputStreamReader(resStream, "UTF-8");
                                    br = new BufferedReader(inputStreamReader);
                                    StringBuilder resBuffer = new StringBuilder();
                                    String resTemp = "";

                                    while((resTemp = br.readLine()) != null) {
                                        resBuffer.append(resTemp);
                                    }

                                    result = resBuffer.toString();
                                } catch (Exception var56) {
                                    isLog = true;
                                    LOGGER.error(log.toString(), var56);
                                }

                                return result;
                            } finally {
                                if (br != null) {
                                    br.close();
                                }

                                if (inputStreamReader != null) {
                                    inputStreamReader.close();
                                }

                                if (resStream != null) {
                                    resStream.close();
                                }

                            }
                        }

                        return result;
                    }

                    request.abort();
                    httpClient.close();
                    entity = result;
                } catch (Throwable var58) {
                    entity = var58;
                    var11 = var58;
                    throw var58;
                } finally {
                    if (response != null) {
                        if (var11 != null) {
                            try {
                                response.close();
                            } catch (Throwable var55) {
                                var11.addSuppressed(var55);
                            }
                        } else {
                            response.close();
                        }
                    }

                }
            } catch (Exception var60) {
                isLog = true;
                LOGGER.error(log.toString(), var60);
                return result;
            }
        } catch (Exception var61) {
            request.abort();
            LOGGER.error(log.toString(), var61);
            isLog = true;
            return result;
        } finally {
            request.abort();

            try {
                httpClient.close();
                if (isLog) {
                    LOGGER.error(log.toString() + " shutdown ");
                }
            } catch (IOException var54) {
                LOGGER.error(log.toString(), var54);
            }

        }

        return (String)entity;
    }

    public static JSONObject postMsg(String url, JSONObject jsonObject) {
        return postMsg(url, jsonObject, 0);
    }

    public static JSONObject postMsg(String url, JSONObject jsonObject, int referer) {
        if (url != null && !"".equals(url = url.trim()) && jsonObject != null) {
            encryptParam(jsonObject, referer);
            HashMap<String, String> param = new HashMap();
            Iterator var5 = jsonObject.keySet().iterator();

            String result;
            while(var5.hasNext()) {
                result = (String)var5.next();
                param.put(result, jsonObject.getString(result));
            }

            Map<String, String> header = new HashMap();
            header.put("referer", "https://commonservice.bafang.com");
            header.put("NewEx-Site-Id", String.valueOf(siteId));
            result = post(url, param, header, "UTF-8");
            if (StringUtils.isEmpty(result)) {
                return null;
            } else {
                JSONObject json = (JSONObject)JSONObject.parse(result);
                json = decryptParam(json);
                return json;
            }
        } else {
            return null;
        }
    }

    private static void encryptParam(JSONObject jsonObject, int referer) {
        jsonObject.put("referer", referer == 0 ? referer : referer);
        String sign = SignUtil.buildSign(jsonObject, SIGN_KEY);
        jsonObject.put("sign", sign);
        Iterator var3 = jsonObject.keySet().iterator();

        while(var3.hasNext()) {
            String key = (String)var3.next();
            if (!key.equals("sign") && jsonObject.get(key) != null) {
                String aes = AES.urlSafeEncrypt(jsonObject.get(key).toString(), AES_KEY);
                jsonObject.put(key, aes);
            }
        }

    }

    private static JSONObject decryptParam(JSONObject jsonObject) {
        if (jsonObject != null) {
            String remoteSign = jsonObject.get("sign") != null ? jsonObject.get("sign").toString() : "";
            String key;
            String localSign;
            if (StringUtils.isEmpty(remoteSign)) {
                localSign = jsonObject.get("errorCode") != null ? jsonObject.get("errorCode").toString() : "";
                key = jsonObject.get("errorMsg") != null ? jsonObject.get("errorMsg").toString() : "";
                LOGGER.error("HttpClientUniformUtil decryptParam No Sign errorCode=" + localSign + " ,errorMsg=" + key);
                return null;
            } else {
                Iterator var2 = jsonObject.keySet().iterator();

                while(var2.hasNext()) {
                    key = (String)var2.next();
                    if (!key.equals("sign") && jsonObject.get(key) != null) {
                        String aes = AES.urlSafeDecrypt(jsonObject.get(key).toString(), AES_KEY);
                        jsonObject.put(key, aes);
                    }
                }

                localSign = SignUtil.getSign(jsonObject, SIGN_KEY);
                if (!remoteSign.equals(localSign)) {
                    LOGGER.error("HttpClientUniformUtil decryptParam Sign Check Failed");
                    return null;
                } else {
                    return jsonObject;
                }
            }
        } else {
            return null;
        }
    }
}
