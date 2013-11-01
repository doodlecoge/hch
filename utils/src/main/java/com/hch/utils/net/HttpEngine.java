package com.hch.utils.net;

/**
 * Created with IntelliJ IDEA.
 * User: huaiwang
 * Date: 13-9-5
 * Time: 上午8:58
 * To change this template use File | Settings | File Templates.
 */

import com.hch.utils.HchException;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpEngine {
    private static final Logger log = LoggerFactory.getLogger(HttpEngine.class);

    private static int ConnectionTimeout = 30000;
    private static int ReadTimeout = 30000;

    private DefaultHttpClient client;
    private HttpResponse resp;
    private URL previousUrl;
    private boolean redirectPost = false;


    // ***************************************************************
    // redirect
    public static final RedirectStrategy DoNotRedirect = new RedirectStrategy() {
        @Override
        public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
            return false;
        }

        @Override
        public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
            return null;
        }
    };

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        client.setRedirectStrategy(redirectStrategy);
    }


    // ***************************************************************
    // cookie
    public static final CookieSpecFactory AllowAllCookies = new CookieSpecFactory() {
        public CookieSpec newInstance(HttpParams params) {
            return new BrowserCompatSpec() {
                @Override
                public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
                }
            };
        }
    };

    public void addCookiePolicy(String key, CookieSpecFactory cookieSpecFactory) {
        client.getCookieSpecs().register(key, cookieSpecFactory);
    }

    public void setCookiePolicy(String key) {
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY, key);
    }


    // ***************************************************************
    // ssl check/verify

    public static final X509TrustManager DoNotCheckTrusted = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    };

    public static final X509HostnameVerifier AlwaysVerifiedSuccess = new X509HostnameVerifier() {
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }

        public void verify(String arg0, String[] arg1, String[] arg2)
                throws SSLException {
        }

        public void verify(String arg0, X509Certificate arg1)
                throws SSLException {
        }

        public void verify(String arg0, SSLSocket arg1) throws IOException {
        }
    };

    public static final Scheme DoNotCheckSsl;

    static {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{DoNotCheckTrusted}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, AlwaysVerifiedSuccess);
            DoNotCheckSsl = new Scheme("https", 443, ssf);
        } catch (Exception e) {
            throw new HchException(e);
        }
    }

    public void alwaysTrustssl() throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        ClientConnectionManager ccm = client.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(DoNotCheckSsl);
    }


    // ***************************************************************
    // set credentials
    public void setCredentials(AuthScope authScope, UsernamePasswordCredentials credentials) {
        client.getCredentialsProvider().setCredentials(authScope, credentials);
    }


    // ***************************************************************
    // request interceptor
    public void addRequestInterceptor(HttpRequestInterceptor reqInterceptor) {
        client.addRequestInterceptor(reqInterceptor);
    }

    public void addRequestInterceptor(HttpRequestInterceptor reqInterceptor, int index) {
        client.addRequestInterceptor(reqInterceptor, index);
    }


    // ***************************************************************
    // response interceptor
    public static final HttpResponseInterceptor DecompressResponseInterceptor = new HttpResponseInterceptor() {
        @Override
        public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
            HttpEntity entity = response.getEntity();
            if (entity == null) return;

            Header ceheader = entity.getContentEncoding();
            if (ceheader == null) return;

            HeaderElement[] codecs = ceheader.getElements();

            for (HeaderElement codec : codecs) {
                String enc = codec.getName();

                if (enc.equalsIgnoreCase("gzip")) {
                    response.setEntity(
                            new GzipDecompressingEntity(entity)
                    );
                    return;
                }

                // todo: add other content encoding handler here
                // else if (enc.equalsIgnoreCase("gzip")) {}
            }

            log.warn("not handled encoding: [" + ceheader.getName() + ": " + ceheader.getValue() + "]");
        }
    };

    public void addResponseInterceptor(HttpResponseInterceptor respInterceptor) {
        client.addResponseInterceptor(respInterceptor);
    }

    public void addResponseInterceptor(HttpResponseInterceptor respInterceptor, int index) {
        client.addResponseInterceptor(respInterceptor, index);
    }


    // ***************************************************************
    {
        this.client = new DefaultHttpClient();

        this.client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, ConnectionTimeout);
        this.client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, ReadTimeout);


//        this.client.addResponseInterceptor(defaultResponseInterceptor);
    }




    public static void setConnectionTimeout(int mills) {
        if (mills < 1000) {
            ConnectionTimeout = 1000;
        } else {
            ConnectionTimeout = mills;
        }
    }

    public static void setReadTimeout(int mills) {
        if (mills < 1000) {
            ReadTimeout = 1000;
        } else {
            ReadTimeout = mills;
        }
    }


    public HttpEngine get(String url) throws IOException, URISyntaxException {
        return this.get(url, null);
    }

    public HttpEngine get(String url, List<NameValuePair> params) throws IOException, URISyntaxException {
        return this.get(url, params, new Header[]{});
    }

    public HttpEngine get(String url, List<NameValuePair> params, Header header) throws IOException, URISyntaxException {
        return this.get(url, params, new Header[]{header});
    }

    public HttpEngine get(String url, List<NameValuePair> params, Header[] headers) throws IOException, URISyntaxException {
        String getUrl = url;

        if (params != null && params.size() > 0)
            getUrl += "?" + URLEncodedUtils.format(params, "UTF-8");

        HttpGet get = new HttpGet(getUrl);


        if (headers != null && headers.length > 0)
            get.setHeaders(headers);

        return this.exec(get);
    }


    public HttpEngine submitForm(WebForm webForm) throws IOException, URISyntaxException {
        if (webForm.getMethod() == WebForm.METHOD_GET)
            get(webForm.getUrl(), webForm.getPostDataPairs());

        else if (webForm.getMethod() == WebForm.METHOD_POST)
            post(webForm.getUrl(), webForm.getPostDataPairs());

        return this;
    }

    public HttpEngine post(String url, Map<String, String> postData)
            throws IOException, URISyntaxException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for (String key : postData.keySet()) {
            String val = postData.get(key);
            params.add(new BasicNameValuePair(key, val));
        }

        return this.post(url, params);
    }

    public HttpEngine post(String url, List<NameValuePair> postData) throws IOException, URISyntaxException {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData, "UTF-8");

        HttpPost post = new HttpPost(url);
        post.setEntity(entity);

        return this.exec(post);
    }

    private HttpEngine exec(HttpUriRequest req) throws IOException, URISyntaxException {
        if (req.getURI().getScheme() == null && previousUrl != null) {
            reConstructUrl(req, previousUrl);
        } else previousUrl = req.getURI().toURL();


        if (this.resp != null) this.consume();
        this.resp = this.client.execute(req);

        // redirect post
        if (redirectPost && getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
            Header loc = resp.getLastHeader(HttpHeaders.LOCATION);
            String redirectLocation = loc.getValue();
            return get(redirectLocation);
        } else {
            return this;
        }
    }

    private void reConstructUrl(HttpUriRequest req, URL previousUrl) throws MalformedURLException, URISyntaxException {
        URL url = new URL(previousUrl, req.getURI().toString());

        this.previousUrl = url;

        if (req instanceof HttpGet) {
            ((HttpGet) req).setURI(new URI(url.toString()));
        } else if (req instanceof HttpPost) {
            ((HttpPost) req).setURI(new URI(url.toString()));
        } else {
            throw new HchException("reconstruct url failed!");
        }
    }

    public void consume() {
        if (this.resp == null)
            return;

        try {
            this.resp.getEntity().getContent().close();
        } catch (Exception e) {
        }
    }

    public Header[] getAllHeaders() {
        if (this.resp != null)
            return this.resp.getAllHeaders();
        else
            return null;
    }

    public Header[] getHeaders(String key) {
        if (this.resp != null)
            return this.resp.getHeaders(key);
        else
            return null;
    }

    public int getStatusCode() {
        if (this.resp != null)
            return this.resp.getStatusLine().getStatusCode();
        else
            return -1;
    }

    public InputStream getInputStream() throws IllegalStateException, IOException {
        return this.resp.getEntity().getContent();
    }

    public String getHtml() throws IllegalStateException, IOException {
        return EntityUtils.toString(resp.getEntity());
//
//        InputStream is = getInputStream();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int b = -1;
//
//        while ((b = is.read()) != -1)
//            baos.write(b);
//
//        is.close();
//
//        String enc = null;
//
//        if (enc != null)
//            return new String(baos.toByteArray(), enc);
//        else
//            return baos.toString();
    }


    public void printHeaders(OutputStream os) throws IOException {
        Header[] allHeaders = this.resp.getAllHeaders();

        if (os == null) os = System.out;

        for (Header header : allHeaders) {
            HeaderElement[] headerElements = header.getElements();

            /*
            for (HeaderElement headerElement : headerElements) {
                String name = headerElement.getName();
                String value = headerElement.getValue();

                String headerStr = name + ": " + value;
                os.write(headerStr.getBytes());
                os.write("\n".getBytes());
            }
            */

            String name = header.getName();
            String value = header.getValue();

            String headerStr = name + ": " + value;
            os.write(headerStr.getBytes());
            os.write("\n".getBytes());
        }
    }

    public CookieStore getCookieStore() {
        return ((DefaultHttpClient) this.client).getCookieStore();
    }

    public void setCookie(CookieStore cs) {
        ((DefaultHttpClient) this.client).setCookieStore(cs);
    }


    public boolean isRedirectPost() {
        return redirectPost;
    }

    public void setRedirectPost(boolean redirectPost) {
        this.redirectPost = redirectPost;
    }
}

