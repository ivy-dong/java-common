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

/**
 * 
 * Config is used to configure the creation of a client
 *
 */
public class Client {
    // Address is the address of the Rest server
    public String Address;

    // ApiKey is the access key for ACL access api
    public String ApiKey;

    // RouteTag of every url
    public String RouteTag;

    // Cert Path
    public String CertPath;

    public String Creator;
    public String Nonce;
    public String PrivateB64;

    public JSONObject getEntParams() {
        String strSignParams = "{\"creator\":\"" + this.Creator + "\",\"nonce\":\"" + this.Nonce
                + "\",\"privateB64\":\"" + this.PrivateB64 + "\"}";
        JSONObject ent_sign_params = JSON.parseObject(strSignParams);

        return ent_sign_params;
    }
}