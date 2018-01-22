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

package com.arxanfintech.common.crypto.core.jce;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;

public final class ECKeyFactory {

  public static final String ALGORITHM = "EC";

  private static final String algorithmAssertionMsg =
      "Assumed the JRE supports EC key factories";

  private ECKeyFactory() { }

  private static class Holder {
    private static final KeyFactory INSTANCE;

    static {
      try {
        INSTANCE = KeyFactory.getInstance(ALGORITHM);
      } catch (NoSuchAlgorithmException ex) {
        throw new AssertionError(algorithmAssertionMsg, ex);
      }
    }
  }

  public static KeyFactory getInstance() {
    return Holder.INSTANCE;
  }

  public static KeyFactory getInstance(final String provider) throws NoSuchProviderException {
    try {
      return KeyFactory.getInstance(ALGORITHM, provider);
    } catch (NoSuchAlgorithmException ex) {
      throw new AssertionError(algorithmAssertionMsg, ex);
    }
  }

  public static KeyFactory getInstance(final Provider provider) {
    try {
      return KeyFactory.getInstance(ALGORITHM, provider);
    } catch (NoSuchAlgorithmException ex) {
      throw new AssertionError(algorithmAssertionMsg, ex);
    }
  }
}
