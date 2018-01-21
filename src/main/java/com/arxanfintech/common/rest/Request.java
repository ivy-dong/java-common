package com.arxanfintech.common.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

import com.arxanfintech.common.rest.Api.Config;

/**
 * 
 * Request is used to help build up a request
 *
 */
public class Request {
    public Config config;
    public String url;
    public List<NameValuePair> body = new ArrayList<NameValuePair>();
    public Header header;
}
