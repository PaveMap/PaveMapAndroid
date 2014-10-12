package com.ttp.pavemap.utility;

import android.util.Log;

import com.ttp.pavemap.Constant;
import com.ttp.pavemap.NetworkException;
import com.ttp.pavemap.PaveMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtils {

    private static HttpUtils mInstance;

    public HttpUtils() {
        mInstance = this;
    }

    public static HttpUtils getInstance() {
        if (mInstance == null) {
            mInstance = new HttpUtils();
        }
        return mInstance;
    }

    public static String httpGET(String http) throws NetworkException {
        return httpGET(http, false);
    }

    public static String httpsGET(String https) throws NetworkException {
        return httpGET(https, true);
    }

    public static String httpGET(String http, boolean isHttps) throws NetworkException {
        String result = "";

        if (!Utils.hasNetworkConnection(PaveMap.instance.getApplicationContext()))
            throw new NetworkException(NetworkException.CODE_NETWORK_DISABLE, "Network is not enabled");

        // TODO Check expire time login
//        if (System.currentTimeMillis() - MyApplication.instance.getLastTimeCallVSKapi() >= Constant.TIME_EXPIRE_LOGIN) {
//            Utils.loginRefreshSession();
//        }

        if (http != null) {
            HttpClient client;
            if (isHttps)
                client = HttpUtils.getInstance().sslClient(new DefaultHttpClient());
            else
                client = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet(http);
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, Constant.DEFAULT_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, Constant.DEFAULT_TIMEOUT);
            httpGet.setParams(httpParams);
            try {
                HttpResponse response = client.execute(httpGet);
                switch (response.getStatusLine().getStatusCode()) {
                    case 400:
                        throw new NetworkException(NetworkException.CODE_BAD_REQUEST, "");
                    case 404:
                        throw new NetworkException(NetworkException.CODE_NOT_FOUND, "");
                    case 405:
                        throw new NetworkException(NetworkException.CODE_METHOD_NOT_ALLOWED, "");
                    case 500:
                        throw new NetworkException(NetworkException.CODE_INTERNAL_SERVER_ERROR, "");
                    case 408:
                        throw new NetworkException(NetworkException.CODE_REQUEST_TIMEOUT, "");
                    case 598:
                        throw new NetworkException(NetworkException.CODE_NETWORK_READ_TIMEOUT_ERROR, "");
                    case 599:
                        throw new NetworkException(NetworkException.CODE_NETWORK_CONNECT_TIMEOUT_ERROR, "");
                    case 502:
                        throw new NetworkException(NetworkException.CODE_BAD_GATEWAY, "");
                    case 503:
                        throw new NetworkException(NetworkException.CODE_SERVICE_UNAVAILABLE, "");
                    case 504:
                        throw new NetworkException(NetworkException.CODE_GATEWAY_TIMEOUT, "");
                }
                result = EntityUtils.toString(response.getEntity(), "UTF-8").trim();
                Log.d("API", "StatusCode = " + response.getStatusLine().getStatusCode());
//                TODO MyApplication.instance.setLastTImeCallVSKapi(System.currentTimeMillis());
            } catch (IOException e) {
                Log.d("API", "StatusCode = IOException");
                throw new NetworkException(NetworkException.CODE_IO_EXCEPTION, e.getMessage());
            } finally {
                client.getConnectionManager().shutdown();
                Log.d("API", "API = " + httpGet.getURI());
                Log.d("API", "Result = " + result);
                Log.d("API", "--------------------------");
            }
        }

        return result;
    }

    ;

    public static String httpPOST(String apiCall, List<NameValuePair> params)
            throws NetworkException {
//		TODO String http = VisikardConnects.getAdsContextPathHttp() + apiCall;
        return httpPOST(""/*http*/, params, true);
    }

    public static String httpsPOST(String apiCall, List<NameValuePair> params)
            throws NetworkException {
//       TODO String http = VisikardConnects.getApiContextPathHttp() + apiCall;
        return httpPOST(""/*http*/, params, false);
    }

    public static String httpPOST(String http, List<NameValuePair> params, boolean isHttps)
            throws NetworkException {
        String result = "";

        if (!Utils.hasNetworkConnection(PaveMap.instance.getApplicationContext()))
            throw new NetworkException(NetworkException.CODE_NETWORK_DISABLE, "Network is not enabled");

        // TODO Check expire time login
//        if (System.currentTimeMillis() - MyApplication.instance.getLastTimeCallVSKapi() >= Constant.TIME_EXPIRE_LOGIN) {
//            Utils.loginRefreshSession();
//        }

        if (http != null) {
            HttpClient client = null;
            if (isHttps) {
                client = HttpUtils.getInstance().sslClient(new DefaultHttpClient());
            } else {
                client = new DefaultHttpClient();
            }
            try {
                HttpPost httpPost = new HttpPost(http);
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, Constant.DEFAULT_TIMEOUT);
                HttpConnectionParams.setSoTimeout(httpParams, Constant.DEFAULT_TIMEOUT);
                httpPost.setParams(httpParams);
                HttpResponse response = client.execute(httpPost);
                switch (response.getStatusLine().getStatusCode()) {
                    case 400:
                        throw new NetworkException(NetworkException.CODE_BAD_REQUEST, "");
                    case 404:
                        throw new NetworkException(NetworkException.CODE_NOT_FOUND, "");
                    case 405:
                        throw new NetworkException(NetworkException.CODE_METHOD_NOT_ALLOWED, "");
                    case 500:
                        throw new NetworkException(NetworkException.CODE_INTERNAL_SERVER_ERROR, "");
                    case 408:
                        throw new NetworkException(NetworkException.CODE_REQUEST_TIMEOUT, "");
                    case 598:
                        throw new NetworkException(NetworkException.CODE_NETWORK_READ_TIMEOUT_ERROR, "");
                    case 599:
                        throw new NetworkException(NetworkException.CODE_NETWORK_CONNECT_TIMEOUT_ERROR, "");
                    case 502:
                        throw new NetworkException(NetworkException.CODE_BAD_GATEWAY, "");
                    case 503:
                        throw new NetworkException(NetworkException.CODE_SERVICE_UNAVAILABLE, "");
                    case 504:
                        throw new NetworkException(NetworkException.CODE_GATEWAY_TIMEOUT, "");
                }
                result = EntityUtils.toString(response.getEntity()).trim();
                Log.d("API", "StatusCode = " + response.getStatusLine().getStatusCode());
                PaveMap.instance.setLastTImeCallVSKapi(System.currentTimeMillis());
            } catch (IOException e) {
                Log.d("API", "StatusCode = IOException");
                throw new NetworkException(NetworkException.CODE_IO_EXCEPTION, e.getMessage());
                //
            } finally {
                client.getConnectionManager().shutdown();
                Log.d("API", "API = " + http);
                Log.d("API", "Result = " + result);
                Log.d("API", "--------------------------");
            }
        }
        return result;
    }

    public HttpClient sslClient(HttpClient client) {
        // if (Core.getSSL()) {
        if (/*Constant.CLIENT_SSL*/false) {
            return client;
        } else {
            try {
                X509TrustManager tm = new X509TrustManager() {

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws java.security.cert.CertificateException {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws java.security.cert.CertificateException {
                        // TODO Auto-generated method stub

                    }
                };
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[]{tm}, null);
                SSLSocketFactory ssf = new MySSLSocketFactory(ctx);
                ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                ClientConnectionManager ccm = client.getConnectionManager();
                SchemeRegistry sr = ccm.getSchemeRegistry();
                sr.register(new Scheme("https", ssf, 443));
                return new DefaultHttpClient(ccm, client.getParams());
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public HttpClient httpsClient(HttpClient client) {
        try {
            X509TrustManager tm = new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                    // TODO Auto-generated method stub

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                    // TODO Auto-generated method stub

                }
            };
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new MySSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = client.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, client.getParams());
        } catch (Exception ex) {
            return null;
        }
    }

    public class MySSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                    // TODO Auto-generated method stub

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                    // TODO Auto-generated method stub

                }
            };

            sslContext.init(null, new TrustManager[]{tm}, null);
        }

        public MySSLSocketFactory(SSLContext context) throws KeyManagementException, NoSuchAlgorithmException,
                KeyStoreException, UnrecoverableKeyException {
            super(null);
            sslContext = context;
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
                UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

}
