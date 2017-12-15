package com.example.program_wx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Https的工具类
 * Created by YinTao on 2017/12/7.
 */

public class HttpsUtil
{
    /** 静态类 */
    public static class SSLParams
    {
        public SSLSocketFactory sslSocketFactory;//SSL工厂 A
        public X509TrustManager trustManager;//X509证书信任管理器类 I
    }

    /**
     * 获取SSL的工厂实例
     * @param certificates InputStream[]
     * @param bksFile      InputStream
     * @param password     password
     * @return SSLParams
     */
    public static SSLParams getSslSocketFactory(InputStream[] certificates, InputStream bksFile, String password)
    {
        SSLParams sslParams = new SSLParams();
        try
        {
            TrustManager[] trustManagers = prepareTrustManager(certificates);//null
            KeyManager[] keyManagers = prepareKeyManager(bksFile, password);//null
            SSLContext tlsContext = SSLContext.getInstance("TLS");
            X509TrustManager trustManager = null;
            if (trustManagers != null)
            {
                trustManager = new MyTrustManager(chooseTrustManager(trustManagers));//新建自定义的证书验证
            }
            else
            {
                trustManager = new UnSafeTrustManager();//不安全的证书验证
            }
            tlsContext.init(keyManagers, new TrustManager[]{trustManager}, null);
            sslParams.sslSocketFactory = tlsContext.getSocketFactory();
            sslParams.trustManager = trustManager;
            return sslParams;
        }
        catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 准备KeyManager
     * @param bksFile  InputStream
     * @param password String
     * @return KeyManager[]
     */
    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password)
    {
        try
        {
            if (bksFile == null || password == null)
            {
                return null;
            }
            KeyStore bks = KeyStore.getInstance("BKS");
            bks.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(bks, password.toCharArray());
            return keyManagerFactory.getKeyManagers();
        }
        catch (IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 准备TrustManagers
     * @param certificates InputStream[]
     * @return TrustManager[]
     */
    private static TrustManager[] prepareTrustManager(InputStream[] certificates)
    {
        if (certificates == null || certificates.length <= 0)
        {
            return null;
        }
        try
        {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream inputStream : certificates)
            {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(inputStream));
                try
                {
                    if (inputStream != null)
                    {
                        inputStream.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            TrustManagerFactory trustManagerFactory = null;
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            return trustManagerFactory.getTrustManagers();
        }
        catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回 X509TrustManager
     * @param trustManagers 默认的所有trustManagers
     * @return X509TrustManager
     */
    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers)
    {
        for (TrustManager trustManager : trustManagers)
        {
            if (trustManager instanceof X509TrustManager)
            {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    /** 自己实现不安全证书管理 */
    private static class UnSafeTrustManager implements X509TrustManager
    {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[0];
        }

    }

    /** 自己实现安全证书管理 */
    private static class MyTrustManager implements X509TrustManager
    {
        private X509TrustManager defaultTrustManager;//默认

        private X509TrustManager localTrustManager;//本地

        private MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException
        {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(trustManagerFactory.getTrustManagers());
            localTrustManager = localTrustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
            try
            {
                defaultTrustManager.checkServerTrusted(chain, authType);
            }
            catch (CertificateException e)
            {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[0];
        }

    }
}
