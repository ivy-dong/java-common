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
package com.arxanfintech.common.structs;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * Register wallet request structure
 *
 */
public class RegisterWalletBody {
    private String enrollment_id;
    private String callback_url;
    private String id;
    private String type;
    private String access;
    private String secret;
    private PublicKey public_key;

    public String getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(String enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public PublicKey getPublic_key() {
        return public_key;
    }

    public void setPublic_key(PublicKey public_key) {
        this.public_key = public_key;
    }

    public List<NameValuePair> ToListNameValuePair() {
        List<NameValuePair> lnvp = new ArrayList<NameValuePair>();
        lnvp.add(new BasicNameValuePair("callback_url", this.callback_url));
        lnvp.add(new BasicNameValuePair("id", this.id));
        lnvp.add(new BasicNameValuePair("type", this.type));
        lnvp.add(new BasicNameValuePair("access", this.access));
        lnvp.add(new BasicNameValuePair("secret", this.secret));
        lnvp.add(new BasicNameValuePair("public_key", "TODO"));
        return lnvp;
    }
}
