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

package com.arxanfintech.sdk.crypto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.encoders.Base64;
import org.json.JSONObject;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;

import com.arxanfintech.common.crypto.CertTool;
import com.arxanfintech.core.crypto.ECIESCoder;
import com.arxanfintech.core.crypto.jce.SpongyCastleProvider;


public class Crypto {
	static {
		//BouncyCastleProvider BCP = new BouncyCastleProvider();
        Security.addProvider(SpongyCastleProvider.getInstance());
        Security.addProvider(new BouncyCastleProvider());
    }

	private byte[] ePrivKeyBytes = null;
	private byte[] tlsCertBytes = null;
	private X509Certificate tlsCert = null;
	private PublicKey tlsPubKey = null;
	private PrivateKey ePrivKey = null;

	/**
	 * Initialize the Crypto Object with enrollment private key and TLS Certificate input stream
	 * @param ePrivKeyBytes enrollment private key
	 * @param tlsCertBytes TLS Certificate
	 * @throws IOException
	 * @throws CertificateException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public Crypto(InputStream epkIS, InputStream tcIS) throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
		this(IOUtils.toByteArray(epkIS), IOUtils.toByteArray(tcIS));
	}

	/**
	 * Initialize the Crypto Object with enrollment private key and TLS Certificate byte array
	 * @param ePrivKeyBytes enrollment private key
	 * @param tlsCertBytes TLS Certificate
	 * @throws IOException
	 * @throws CertificateException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public Crypto(byte[] ePrivKeyBytes, byte[] tlsCertBytes) throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
		this.ePrivKeyBytes = ePrivKeyBytes;
		this.tlsCertBytes = tlsCertBytes;
		init();
	}

	private void init() throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {

		tlsCert = CertTool.getCertfromPEM(tlsCertBytes);
		tlsPubKey = tlsCert.getPublicKey();

		Reader fRd = new BufferedReader(new InputStreamReader(new java.io.ByteArrayInputStream(ePrivKeyBytes)));
		PEMParser pemRd =  new PEMParser(fRd);
        PEMKeyPair pemPair = (PEMKeyPair)pemRd.readObject();
        KeyPair pair = new JcaPEMKeyConverter().setProvider("SC").getKeyPair(pemPair);
        ePrivKey = pair.getPrivate();
	}


	/**
	 * @return enrollment private key
	 */
	public byte[] getEPrivKeyBytes() {
		return ePrivKeyBytes;
	}

	/**
	 * @return TLS Certificate in bytes array
	 */
	public byte[] getTlsCertBytes() {
		return tlsCertBytes;
	}

	/**
	 * @return TLS Certificate
	 */
	public X509Certificate getTlsCert() {
		return tlsCert;
	}

	private byte[] signData(byte[] data) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
		Signature sgr = Signature.getInstance("SHA3-256withECDSA", "BC");

	    sgr.initSign(ePrivKey);

        sgr.update(data);

        return sgr.sign();
	}

	private boolean verifyData(byte[] data, byte[] signature) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
		Signature sgr = Signature.getInstance("SHA3-256withECDSA", "BC");

	    sgr.initVerify(tlsPubKey);

        sgr.update(data);

        return sgr.verify(signature);
	}

	/**
	 * Generate the signature with the enrollment private key
	 * @param data data to sign
	 * @return the signature in base64
	 * @throws SignatureException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public String signDataWithBase64(byte[] data) throws InvalidKeyException,
														 NoSuchAlgorithmException,
														 NoSuchProviderException,
														 SignatureException {
		String res = null;
		byte[] signature = this.signData(data);
		// to base64
		res = Base64.toBase64String(signature);
		return res;
	}

	private byte[] encryptBytes(byte[] data) throws NoSuchAlgorithmException,
													NoSuchProviderException,
													NoSuchPaddingException,
													InvalidKeyException,
													IllegalBlockSizeException,
													BadPaddingException,
													InvalidAlgorithmParameterException,
													InvalidCipherTextException,
													IOException {
		BCECPublicKey ecPub = (BCECPublicKey)tlsPubKey;
		byte[] cipherData = ECIESCoder.encrypt(ecPub.getQ(), data);
		return cipherData;
	}

	private byte[] decryptBytes(byte[] data) throws NoSuchAlgorithmException,
													NoSuchProviderException,
													NoSuchPaddingException,
													InvalidKeyException,
													IllegalBlockSizeException,
													BadPaddingException,
													InvalidAlgorithmParameterException,
													InvalidCipherTextException,
													IOException {
		BCECPrivateKey ecPrivate = (BCECPrivateKey)ePrivKey;
		return ECIESCoder.decrypt(ecPrivate.getD(), data);
	}

	/**
	 * Encrypt data with TLS certificate and convert cipher to base64
	 * @param data byte array to encrypt
	 * @return cipher in base64
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * @throws InvalidCipherTextException
	 */
	public String encryptWithBase64(byte[] data) throws NoSuchAlgorithmException,
														NoSuchProviderException,
														NoSuchPaddingException,
														InvalidKeyException,
														IllegalBlockSizeException,
														BadPaddingException,
														InvalidAlgorithmParameterException,
														InvalidCipherTextException,
														IOException {
		byte[] cipher = encryptBytes(data);
		return Base64.toBase64String(cipher);
	}

	/**
	 * Sign and encrypt data then convert cipher to base64
	 * @param data data byte array to encrypt
	 * @return cipher in base64
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws SignatureException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * @throws InvalidCipherTextException
	 */
	public String signAndEncrypt(byte[] data) throws NoSuchAlgorithmException,
													NoSuchProviderException,
													NoSuchPaddingException,
													InvalidKeyException,
													IllegalBlockSizeException,
													BadPaddingException,
													SignatureException,
													InvalidAlgorithmParameterException,
													InvalidCipherTextException,
													IOException {
		String dataBase64 = Base64.toBase64String(data);
		String signBase64 = signDataWithBase64(data);
		JSONObject json = new JSONObject();
		json.put("data", dataBase64);
		json.put("signature", signBase64);
		String dataStr = json.toString();
		String cipher = encryptWithBase64(dataStr.getBytes());
		return cipher;
	}

	/**
	 * decrypt and verify signature of data
	 * @param data data byte array to decrypt and verify
	 * @return original plain text
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws SignatureException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * @throws InvalidCipherTextException
	 */
	public String decryptAndVerify(byte[] data) throws NoSuchAlgorithmException,
													NoSuchProviderException,
													NoSuchPaddingException,
													InvalidKeyException,
													IllegalBlockSizeException,
													BadPaddingException,
													SignatureException,
													InvalidAlgorithmParameterException,
													InvalidCipherTextException,
													IOException {
           // decode base64
           byte[] base64DecodedData = Base64.decode(data);

           // decrypt data
           byte[] decryptedData = decryptBytes(base64DecodedData);

           // parse json structure
           JSONObject json = new JSONObject(new String(decryptedData));
           String base64Data = json.getString("data");
           String base64Sign = json.getString("signature");

           // decode base64 of the original data and signature
           byte[] oriData = Base64.decode(base64Data.getBytes());
           byte[] signature = Base64.decode(base64Sign.getBytes());

           // verify the signature
           boolean verifyOK = verifyData(oriData, signature);
           if (!verifyOK) {
               return null;
           }

           // return the original data
           return new String(oriData);
	}
}
