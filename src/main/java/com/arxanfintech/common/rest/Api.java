/*******************************************************************************
Copyright ArxanFintech Technology Ltd. 2018 All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

                 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*******************************************************************************/

package com.arxanfintech.common.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.spi.http.HttpHandler;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.FileInputStream;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.arxanfintech.common.crypto.Crypto;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import com.alibaba.fastjson.JSONObject;

import com.arxanfintech.common.crypto.Crypto;

/**
 * 
 * Rest api Rest api for java common
 *
 */
public class Api {

    public CloseableHttpClient httpclient;

    public CloseableHttpClient NewHttpClient() throws Exception {
        if (httpclient == null) {
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
        return httpclient;
    }

    public String DoGet(Request request) throws Exception {
        try {
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            Unirest.setHttpClient(httpclient);

            HttpResponse<String> res = Unirest.get(request.url)
                    .header("Callback-Url", request.header.getString("Callback-Url"))
                    .header("Bc-Invoke-Mode", request.header.getString("Bc-Invoke-Mode"))
                    .header("API-Key", request.client.ApiKey).asString();

            String respData = res.getBody();

            System.out.println("Got remote cipher response: " + respData);
            String oriData = request.crypto.decryptAndVerify(respData.getBytes());
            return oriData;
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    /**
     * httpclient post
     *
     * @param request
     *            http post info
     * @return response data error return null
     */
    public String DoPost(Request request) {
        try {
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            Unirest.setHttpClient(httpclient);

            String buf = request.crypto.signAndEncrypt(request.body.toString().getBytes());
            System.out.println("after sign and encrypt : " + buf);
            HttpResponse<String> res = Unirest.post(request.url).header("API-Key", request.client.ApiKey)
                    .header("Callback-Url", request.header.getString("Callback-Url"))
                    .header("Bc-Invoke-Mode", request.header.getString("Bc-Invoke-Mode")).body(buf).asString();

            String respData = res.getBody();

            System.out.println("Got remote cipher response: " + respData);

            String oriData = request.crypto.decryptAndVerify(respData.getBytes());

            return oriData;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    /**
     * httpclient put
     *
     * @param request
     *            http post info
     * @return response data error return null
     */
    public String DoPut(Request request) {
        try {
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            Unirest.setHttpClient(httpclient);

            String buf = request.crypto.signAndEncrypt(request.body.toString().getBytes());

            HttpResponse<String> res = Unirest.put(request.url).header("API-Key", request.client.ApiKey)
                    .header("Callback-Url", request.header.getString("Callback-Url"))
                    .header("Bc-Invoke-Mode", request.header.getString("Bc-Invoke-Mode")).body(buf).asString();

            String respData = res.getBody();

            System.out.println("Got remote cipher response: " + respData);

            String oriData = request.crypto.decryptAndVerify(respData.getBytes());

            return oriData;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
