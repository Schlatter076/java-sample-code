/*
 * 
 * 
 *
 * 
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.visa.developer.sample.offersdataapi.auth;

import com.visa.developer.sample.offersdataapi.Pair;
import com.migcomponents.migbase64.Base64;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.ssl.SSLContexts;

import java.util.Map;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2017-10-17T12:34:52.820+05:30")
public class HttpBasicAuth implements Authentication {
  private String username;
  private String password;
  private String truststore_path;
  private String truststore_password;
  private String KEYSTORE_PATH;
  private String KEYSTORE_PASSWORD;
  private String PRIVATE_KEY_PASSWORD;
  private final String CORRELATION_ID ="ex-correlation-id";

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  public String getKEYSTORE_PATH() {
	return KEYSTORE_PATH;
  }

  public void setKEYSTORE_PATH(String kEYSTORE_PATH) {
	KEYSTORE_PATH = kEYSTORE_PATH;
  }

  public String getKEYSTORE_PASSWORD() {
	return KEYSTORE_PASSWORD;
  }

  public void setKEYSTORE_PASSWORD(String kEYSTORE_PASSWORD) {
	KEYSTORE_PASSWORD = kEYSTORE_PASSWORD;
  }

  public String getPRIVATE_KEY_PASSWORD() {
	return PRIVATE_KEY_PASSWORD;
  }

  public void setPRIVATE_KEY_PASSWORD(String pRIVATE_KEY_PASSWORD) {
	PRIVATE_KEY_PASSWORD = pRIVATE_KEY_PASSWORD;
  }

  public String getTruststore_path() {
	return truststore_path;
  }

  public void setTruststore_path(String truststore_path) {
	this.truststore_path = truststore_path;
  }

  public String getTruststore_password() {
	return truststore_password;
  }

  public void setTruststore_password(String truststore_password) {
	this.truststore_password = truststore_password;
  }

  @Override
  public void applyToParams(String path,Object requestBody, Map<String, String> headerParams, List<Pair> queryParams) {
    if (username == null && password == null) {
      return;
    }
    String str = (username == null ? "" : username) + ":" + (password == null ? "" : password);
    try {
        String base64Encode = new String(org.apache.commons.codec.binary.Base64.encodeBase64(str.getBytes()), "UTF-8");
        headerParams.put(HttpHeaders.AUTHORIZATION, "Basic " + base64Encode);
        headerParams.put(CORRELATION_ID,getCorrelationId());
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
  public HostnameVerifier getHostnameVerifier() {
      return new HostnameVerifier() {

         @Override
          public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
              return true;
          }
      };
  }

  public SSLContext getSSLContext() {

/*      TrustManager mytm[] = null;
      KeyManager mykm[] = null;

      try {
          mytm = new TrustManager[]{new MyX509TrustManager(truststore_path, truststore_password.toCharArray())};
          mykm = new KeyManager[]{new MyX509KeyManager(keystore_path, keystore_password.toCharArray())};
      } catch (Exception ex) {
          ex.printStackTrace();
      }

      SSLContext ctx = null;
      try {
          ctx = SSLContext.getInstance("TLSv1");
          ctx.init(mykm, mytm, null);
      } catch (java.security.GeneralSecurityException ex) {
      }*/
	// Load client certificate into key store
	  SSLContext sslcontext = null;
	try {
		sslcontext = SSLContext.getInstance("TLSv1");
		sslcontext = SSLContexts.custom()
		          .loadKeyMaterial(new File(KEYSTORE_PATH), KEYSTORE_PASSWORD.toCharArray(),
		                  PRIVATE_KEY_PASSWORD.toCharArray())
		          .build();

	} catch (KeyManagementException e) {
		e.printStackTrace();
	} catch (UnrecoverableKeyException e) {
		e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
	} catch (KeyStoreException e) {
		e.printStackTrace();
	} catch (CertificateException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}


      return sslcontext;
  }

  /**
   * Taken from http://java.sun.com/javase/6/docs/technotes/guides/security/jsse/JSSERefGuide.html
   *
   */
  static class MyX509TrustManager implements X509TrustManager {

      /*
       * The default PKIX X509TrustManager9.  We'll delegate
       * decisions to it, and fall back to the logic in this class if the
       * default X509TrustManager doesn't trust it.
       */
      X509TrustManager pkixTrustManager;

      MyX509TrustManager(String trustStore, char[] password) throws Exception {
          this(new File(trustStore), password);
      }

      MyX509TrustManager(File trustStore, char[] password) throws Exception {
          // create a "default" JSSE X509TrustManager.

          KeyStore ks = KeyStore.getInstance("JKS");

          ks.load(new FileInputStream(trustStore), password);

          TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
          tmf.init(ks);

          TrustManager tms[] = tmf.getTrustManagers();

          /*
           * Iterate over the returned trustmanagers, look
           * for an instance of X509TrustManager.  If found,
           * use that as our "default" trust manager.
           */
          for (int i = 0; i < tms.length; i++) {
              if (tms[i] instanceof X509TrustManager) {
                  pkixTrustManager = (X509TrustManager) tms[i];
                  return;
              }
          }

          /*
           * Find some other way to initialize, or else we have to fail the
           * constructor.
           */
          throw new Exception("Couldn't initialize");
      }

      /*
       * Delegate to the default trust manager.
       */
      public void checkClientTrusted(X509Certificate[] chain, String authType)
              throws CertificateException {
          try {
              pkixTrustManager.checkClientTrusted(chain, authType);
          } catch (CertificateException excep) {
              // do any special handling here, or rethrow exception.
          }
      }

      /*
       * Delegate to the default trust manager.
       */
      public void checkServerTrusted(X509Certificate[] chain, String authType)
              throws CertificateException {
          try {
              pkixTrustManager.checkServerTrusted(chain, authType);
          } catch (CertificateException excep) {
              /*
               * Possibly pop up a dialog box asking whether to trust the
               * cert chain.
               */
          }
      }

      /*
       * Merely pass this through.
       */
      public X509Certificate[] getAcceptedIssuers() {
          return pkixTrustManager.getAcceptedIssuers();
      }
  }

  /**
   * Inspired from http://java.sun.com/javase/6/docs/technotes/guides/security/jsse/JSSERefGuide.html
   *
   */
  static class MyX509KeyManager implements X509KeyManager {

      /*
       * The default PKIX X509KeyManager.  We'll delegate
       * decisions to it, and fall back to the logic in this class if the
       * default X509KeyManager doesn't trust it.
       */
      X509KeyManager pkixKeyManager;

      MyX509KeyManager(String keyStore, char[] password) throws Exception {
          this(new File(keyStore), password);
      }

      MyX509KeyManager(File keyStore, char[] password) throws Exception {
          // create a "default" JSSE X509KeyManager.

          KeyStore ks = KeyStore.getInstance("JKS");
          ks.load(new FileInputStream(keyStore), password);

          KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509", "SunJSSE");
          kmf.init(ks, password);

          KeyManager kms[] = kmf.getKeyManagers();

          /*
           * Iterate over the returned keymanagers, look
           * for an instance of X509KeyManager.  If found,
           * use that as our "default" key manager.
           */
          for (int i = 0; i < kms.length; i++) {
              if (kms[i] instanceof X509KeyManager) {
                  pkixKeyManager = (X509KeyManager) kms[i];
                  return;
              }
          }

          /*
           * Find some other way to initialize, or else we have to fail the
           * constructor.
           */
          throw new Exception("Couldn't initialize");
      }

      public PrivateKey getPrivateKey(String arg0) {
          return pkixKeyManager.getPrivateKey(arg0);
      }

      public X509Certificate[] getCertificateChain(String arg0) {
          return pkixKeyManager.getCertificateChain(arg0);
      }

      public String[] getClientAliases(String arg0, Principal[] arg1) {
          return pkixKeyManager.getClientAliases(arg0, arg1);
      }

      public String chooseClientAlias(String[] arg0, Principal[] arg1, Socket arg2) {
          return pkixKeyManager.chooseClientAlias(arg0, arg1, arg2);
      }

      public String[] getServerAliases(String arg0, Principal[] arg1) {
          return pkixKeyManager.getServerAliases(arg0, arg1);
      }

      public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2) {
          return pkixKeyManager.chooseServerAlias(arg0, arg1, arg2);
      }
  }
  /**
 * Get Correlation Id for the API Call.
 * @return correlation Id
 */
private String getCorrelationId() {
    //Passing correlation Id header is optional while making an API call.
    return RandomStringUtils.random(10, true, true) + "_SC";
}
}
