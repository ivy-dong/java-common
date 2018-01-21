package com.arxanfintech.common.rest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.arxanfintech.common.rest.Api;
import com.arxanfintech.common.rest.Request;
import org.apache.http.message.BasicNameValuePair;

/**
 * Unit test for RestApi
 */
public class ApiTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public ApiTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ApiTest.class);
    }

    /**
     * Rigourous testGet :-)
     */
    public void testGet() {
        Api api = new Api();
        api.NewHttpClien();
        Request get = new Request();
        get.url = "http://httpbin.org/get";
        try {
            api.DoGet(get);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    /**
     * Rigourous testPost :-)
     */
    public void testPost() {
        Api api = new Api();
        api.NewHttpClien();
        Request post = new Request();
        post.url = "http://httpbin.org/post";
        post.body.add(new BasicNameValuePair("username", "vip"));
        post.body.add(new BasicNameValuePair("password", "secret"));
        try {
            api.DoPost(post);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

    }
}
