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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.util.encoders.Base64;

public class CertTool {

	/**
	 * Reads a certificate in PEM-format from a file. The file may contain other
	 * things, the first certificate in the file is read.
	 *
	 * @param certFile
	 *            the file containing the certificate in PEM-format
	 * @return X509Certificate
	 * @exception IOException
	 *                if the filen cannot be read.
	 * @exception CertificateException
	 *                if the filen does not contain a correct certificate.
	 * @throws NoSuchProviderException
	 */
	public static X509Certificate getCertfromPEM(String certFile) throws IOException, CertificateException, NoSuchProviderException {
		InputStream inStrm = new FileInputStream(certFile);
		X509Certificate cert = getCertfromPEM(inStrm);
		return cert;
	}

	/**
	 * Reads a certificate in PEM-format from a byte array. The array may
	 * contain other things, the first certificate in the array is read.
	 *
	 * @param pemBytes
	 *            the byte array containing the certificate in PEM-format
	 * @return X509Certificate
	 * @exception IOException
	 *                if the array cannot be read.
	 * @exception CertificateException
	 *                if the array does not contain a correct certificate.
	 * @throws NoSuchProviderException
	 */
	public static X509Certificate getCertfromPEM(byte[] pemBytes) throws IOException, CertificateException, NoSuchProviderException {
		InputStream inStrm = new java.io.ByteArrayInputStream(pemBytes);
		X509Certificate cert = getCertfromPEM(inStrm);
		return cert;
	}

	/**
	 * Reads a certificate in PEM-format from an InputStream. The stream may
	 * contain other things, the first certificate in the stream is read.
	 *
	 * @param certFile
	 *            the input stream containing the certificate in PEM-format
	 * @return X509Certificate
	 * @exception IOException
	 *                if the stream cannot be read.
	 * @exception CertificateException
	 *                if the stream does not contain a correct certificate.
	 * @throws NoSuchProviderException
	 */
	public static X509Certificate getCertfromPEM(InputStream certstream) throws IOException, CertificateException, NoSuchProviderException {

		String beginKey = "-----BEGIN CERTIFICATE-----";
		String endKey = "-----END CERTIFICATE-----";
		BufferedReader bufRdr = new BufferedReader(new InputStreamReader(certstream));
		ByteArrayOutputStream ostr = new ByteArrayOutputStream();
		PrintStream opstr = new PrintStream(ostr);
		String temp;
		while ((temp = bufRdr.readLine()) != null && !temp.equals(beginKey))
			continue;
		if (temp == null)
			throw new IOException("Error in " + certstream.toString() + ", missing " + beginKey + " boundary");
		while ((temp = bufRdr.readLine()) != null && !temp.equals(endKey))
			opstr.print(temp);
		if (temp == null)
			throw new IOException("Error in " + certstream.toString() + ", missing " + endKey + " boundary");
		opstr.close();

		byte[] certbuf = Base64.decode(ostr.toByteArray());

		// X509Certificate object
		CertificateFactory cf = CertificateFactory.getInstance("X.509", "SC");
		X509Certificate x509cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certbuf));
		return x509cert;
	} // getCertfromPEM

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
