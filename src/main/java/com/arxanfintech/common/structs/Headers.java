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

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class Headers {

    private String content_type = "application/cipher";
    private String api_key;
    private String accept = "application/json";

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public Header[] ToHeaderArray() {
        Header[] headers = { new BasicHeader("Content-type", this.content_type), new BasicHeader("Accep", this.accept),
                new BasicHeader("Api-Key", this.api_key) };
        return headers;
    }
}