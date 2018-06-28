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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arxanfintech.common.crypto.ED25519;

public class Common {
    public static JSONObject Build_Body(JSONObject payload, String did, String created, String nonce,
            String privatekeyBase64) {
        try {
            String signdata = ED25519.Sign(nonce, privatekeyBase64, did, payload.toJSONString());
            String strdata = "{\"payload\": \"" + payload.toString().replace("\"", "\\\"")
                    + "\", \"signature\": {\"creator\": \"" + did + "\", \"created\": \"" + created + "\",\"nonce\":\""
                    + nonce + "\", \"signature_value\": \"" + signdata + "\"}}";

            JSONObject jsonbody = JSON.parseObject(strdata);

            return jsonbody;
        } catch (Exception e) {
            System.out.println("Build_Body error: " + e.getMessage());
            return null;
        }
    }
}
