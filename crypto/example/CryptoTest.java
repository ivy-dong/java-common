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

import java.io.FileInputStream;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import com.arxanfintech.sdk.crypto.Crypto;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.net.URLDecoder;


public class CryptoTest {

	//BJ20161209001 is an p2p id, replace it with your id.

	static final String tlscertpath = "./keys/client/server.crt"; //post server，encrypt。 receive server，verify.
	static final String eprivkeypath = "./keys/client/client.key"; //post server，sign。receive server，decrypt.

	static final byte[] data = "1234567890abcdefgh".getBytes();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//read TLS Cert
			FileInputStream tlsis = new FileInputStream(tlscertpath);
			//read enrollment private key
			FileInputStream epkis = new FileInputStream(eprivkeypath);

			// init crypto library
			Crypto crypto = new Crypto(epkis, tlsis);

			// sign and encrypt the data (byte array)
			String buf = crypto.signAndEncrypt(data);

			System.out.println("Base64 Cipher: " + buf);
			System.out.println();

			// post the cipher to remote crypto-gateway
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
			CloseableHttpClient httpclient = HttpClients.custom()
							.setSSLSocketFactory(sslsf)
							.build();
			Unirest.setHttpClient(httpclient);
			HttpResponse<String> res = Unirest.post("http://172.16.15.23:9143/tomago/v2/entities")
					.header("Content-Type", "application/cipher").header("API-Key", "7nUxFY1Mp1516180225")
					.header("Accept", "application/json").body(buf).asString();

			String respData = res.getBody();

			System.out.println("Got remote cipher response: " + respData);

			String decodedData = URLDecoder.decode(respData, "UTF-8");

			String oriData = crypto.decryptAndVerify(decodedData.getBytes());

			System.out.println("Got remote response: " + oriData);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
