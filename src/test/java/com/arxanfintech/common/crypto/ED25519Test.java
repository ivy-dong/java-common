package com.arxanfintech.common.crypto;
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.alibaba.fastjson.JSONObject;
import com.arxanfintech.common.crypto.ED25519;
import com.arxanfintech.common.rest.Request;
import org.apache.http.message.BasicNameValuePair;

/**
 * Unit test for ED25519Test
 */
public class ED25519Test extends TestCase {
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public ED25519Test(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ED25519Test.class);
    }

    /**
     * Rigourous testSign :-)
     */
    public void testSign() {
        try {
            String result = ED25519.Sign("nonce",
                    "bx0jOwALZ0hLDxwyHyct3xoH4KjFL3wZ6dDYd2O6Bxmh0qnfEFLK9BjiCfwHoUkU/ryNMBbFWYz9HpFGgwKt6Q==",
                    "did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2", "{\"username\":\"vip\",\"password\":\"secret\"}",
                    "/Users/yan/eclipse-workspace/java-common/src/main/resources/sign-util");
            System.out.println("after sign: " + result);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

}
