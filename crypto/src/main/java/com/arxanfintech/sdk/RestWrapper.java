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

package com.arxanfintech.sdk;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.spongycastle.crypto.InvalidCipherTextException;

import com.arxanfintech.sdk.crypto.Crypto;
import com.arxanfintech.util.Config;

/**
 * @author bryan
 *
 */
public class RestWrapper extends AbstractHandler {

	public static int port;
	public static boolean logFlag;
	static Crypto crypto;

	private static void loadConfig() throws IOException, CertificateException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchProviderException {
		String rootPath = System.getProperty("user.dir").replace("\\", "/");
		FileInputStream input = new FileInputStream(rootPath + "/config.properties");
		Properties prop = new Properties();
		prop.load(input);
		Config.setProperties(prop);
		port = Integer.valueOf(prop.getProperty("port", "8080")).intValue();
		logFlag = Config.getLogFlag();

		if (logFlag) {
			System.out.println(":" + rootPath);
		}
		String kPath = Config.getKeyPath();
		String CPath = Config.getCertPath();

		if (kPath == "" || kPath == null){
			System.err.println("Error: enrollmentKey in config.properties is empty.");
			return;
		}

		if (CPath == "" || CPath == null) {
			System.err.println("Error: tlsCert in config.properties is empty.");
			return;
		}

		System.out.println("kPath    " + kPath);
		System.out.println("CPath    " + CPath);

		FileInputStream key = new FileInputStream(kPath);
		FileInputStream cert = new FileInputStream(CPath);

		// init crypto library
		System.out.println("Initializing crypto library ...");
		crypto = new Crypto(key, cert);
	}

	public void handle(String target, Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws IOException, ServletException {
		try {
			doHandle(request, httpRequest, httpResponse);
		} catch (Exception e) {
			e.printStackTrace();
			httpResponse.setStatus(500);
			request.setHandled(true);
		}
	}

	private void doHandle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws IOException, UnsupportedEncodingException, CertificateException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchProviderException, InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, SignatureException, InvalidAlgorithmParameterException,
			InvalidCipherTextException {
		try {

			httpRequest.setCharacterEncoding("utf-8");
			String uri = httpRequest.getRequestURI();
			String input = httpRequest.getParameter("input");
            String mode = httpRequest.getParameter("mode");

			if ((input == "" || input == null)) {
				System.out.println("Input is empty!");
				return;
			}

			if ((mode == "" || mode == null)) {
				System.out.println("Mode is empty, use the default mode: 1");
                mode = "1";
			}

			if (Config.getLogFlag()) {
				System.out.println("Input: " + input);
				System.out.println("Mode: " + mode);
			}

			byte[] data = input.getBytes();

            String buf = null;

            if (mode.equals("1")) {
                // sign and encrypt the data (byte array)
                if (Config.getLogFlag()) {
                    System.out.println("Processing signature and encryption...");
                }
                buf = crypto.signAndEncrypt(data);
            } else if (mode.equals("2")) {
                // decrypt and verify the data (byte array)
                if (Config.getLogFlag()) {
                    System.out.println("Processing decryption and verify signature...");
                }
                buf = crypto.decryptAndVerify(data);
            } else {
                // sign and encrypt the data (byte array)
                if (Config.getLogFlag()) {
                    System.out.println("Processing signature and encryption...");
                }
                buf = crypto.signAndEncrypt(data);
            }

            if (Config.getLogFlag()) {
                System.out.println("Output: " + buf);
            }

			httpResponse.setContentType("text/html;charset=utf-8");
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			request.setHandled(true);
			httpResponse.getWriter().println(buf);

		} catch (Exception e) {
			e.printStackTrace();
			if (Config.getLogFlag()) {

				System.err.println(e.getLocalizedMessage());
			}

		}

	}

	public static void main(String[] args) throws Exception {
		loadConfig();
		System.out.println("Rest server started at :" + port);

		Server jettyServer = new Server(port);
		jettyServer.setHandler(new RestWrapper());
		jettyServer.start();
		jettyServer.join();

	}

}
