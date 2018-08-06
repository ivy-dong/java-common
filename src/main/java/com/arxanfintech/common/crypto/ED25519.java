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

package com.arxanfintech.common.crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Base64;

public class ED25519 {

    /**
     * 
     * use nonce，privatekey，did，payload finish ed25519 sign for payload
     * 
     * @param nonce
     *            nonce
     * @param privatekey
     *            privatekey
     * @param did
     *            did
     * @param payload
     *            base64 payload
     * @return
     */
    public static String Sign(String nonce, String privatekey, String did, String payload, String SignToolPath) {

        try {
            Base64.Encoder encoder = Base64.getEncoder();
            String base64_payload = encoder.encodeToString(payload.getBytes("UTF-8"));

            String signvalue = "";

            String cmd = SignToolPath + " -key " + privatekey + " -nonce " + nonce + " -did " + did + " -data "
                    + base64_payload;
            Runtime runtime = Runtime.getRuntime();

            BufferedReader br = new BufferedReader(new InputStreamReader(runtime.exec(cmd).getInputStream()));

            signvalue = br.readLine().trim();

            // String FILE_NAME = "sign-util";
            // System.out.println("MyClass.class.getClassLoder().getResource(FILE_NAME).getPath():"
            // + ED25519.class.getClassLoader().getResource(FILE_NAME).getPath() + "\r\n"
            // +
            // ED25519.class.getClassLoader().getResource("sign-util").getPath().substring(0,
            // ED25519.class.getClassLoader().getResource("sign-util").getPath().length() -
            // 9)
            // + "/../sign2-util");
            // System.out.println("MyClass.class.getResource(FILE_NAME).getPath(): "
            // + ED25519.class.getResource(FILE_NAME).getPath());
            // System.out.println("MyClass.class.getResource(File.separator +
            // \"FILE_NAME\").getPath(): "
            // + ED25519.class.getResource(File.separator + "FILE_NAME").getPath());
            // System.out.println(
            // "MyClass.class.getResource(\"..\" + File.separator + \"..\" + File.separator
            // + FILE_NAME).getPath(): "
            // + ED25519.class.getResource(".." + File.separator + ".." + File.separator +
            // FILE_NAME)
            // .getPath());

            return signvalue;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
