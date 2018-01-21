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

/**
 * 
 * Rest api Rest api for java common
 *
 */
public class Api {

    public CloseableHttpClient httpclient;

    /**
     * 
     * Config is used to configure the creation of a client
     *
     */
    public class Config {
        // Address is the address of the Rest server
        public String Address;

        // ApiKey is the access key for ACL access api
        public String ApiKey;
    }

    /**
     * 
     * NewHttpClient returns an CloseableHttpClient
     * 
     */
    public CloseableHttpClient NewHttpClien() {
        if (httpclient == null) {
            httpclient = HttpClients.createDefault();
        }
        return httpclient;
    }

    /**
     * httpclient get
     *
     * @param request
     *            http get info
     * @throws Exception
     *             exception
     */
    public void DoGet(Request request) throws Exception {
        try {
            HttpGet httpGet = new HttpGet(request.url);
            if (request.header == null) {
                httpGet.setHeader(request.header);
            }
            CloseableHttpResponse response1 = httpclient.execute(httpGet);

            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity1);
            } finally {
                response1.close();
            }
        } finally {
            httpclient.close();
        }
    }

    /**
     * httpclient post
     *
     * @param request
     *            http post info
     * @throws Exception
     *             exception
     */
    public void DoPost(Request request) throws Exception {
        try {
            HttpPost httpPost = new HttpPost(request.url);
            httpPost.setEntity(new UrlEncodedFormEntity(request.body));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();

                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
