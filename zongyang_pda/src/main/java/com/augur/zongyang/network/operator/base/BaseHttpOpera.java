package com.augur.zongyang.network.operator.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.manager.JsonManager;
import com.augur.zongyang.model.Response;
import com.augur.zongyang.model.UploadData;
import com.augur.zongyang.model.UploadFile;
import com.augur.zongyang.model.http.request.RequestMethod;
import com.augur.zongyang.model.http.request.RequestObjectType;
import com.augur.zongyang.network.api.WebServiceApi;
import com.augur.zongyang.util.ListUtil;
import com.augur.zongyang.util.LogUtil;
import com.augur.zongyang.util.StreamUtil;
import com.augur.zongyang.util.constant.UploadConstant;
import com.augur.zongyang.util.listener.RequestListener;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by yunhu on 2017-12-11.
 */

public class BaseHttpOpera {


    private HttpClient httpClient;
    private static Header cookieHeader;
    protected final Context context;
    private OnNetResultListener onNetResultListener;
    protected static WebServiceApi api = null;
    private static JsonManager jsonManager;
    public static String sessionStr;
    private static String sessionKey;
    private static boolean SSO;
    public static OnNetResultListener onNetResultListenerMain;

    public boolean isSSO() {
        return SSO;
    }

    private void enableSSO() {
        SSO = true;
    }

    private void disableSSO() {
        SSO = false;
    }

    protected void putHandleState(Map<Object, Object> paramMap,
                                  String handleState) {
        paramMap.put("form.memo1", handleState);
    }

    protected void putStatus(Map<Object, Object> paramMap, Integer statusInteger) {
        paramMap.put("form.memo2", statusInteger.toString());
    }

    public BaseHttpOpera(Context context,
                         OnNetResultListener onNetResultListener) {
        this.onNetResultListener = onNetResultListener;
        this.context = context;

        getHttpClient();
        initAPI(context);
        initJsonManager(context);
    }

    private Response enableSSO(String userName) {
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("userName", userName));
        return (Response) getResultObject(api.getAPI_SSO(), params,
                RequestMethod.GET, Response.class);
    }

    public void makeSureSSO() {
        if ((!isSSO()) && (CurrentUser.getInstance().getCurrentUser() != null)) {
            sessionStr = null;
            Response response = enableSSO(CurrentUser.getInstance()
                    .getCurrentUser().getLoginName());
            if (response.isSuccess()) {
                enableSSO();
            }
        }
    }

    private void initJsonManager(Context context) {
        jsonManager = JsonManager.getInstance(context);
    }

    private void initAPI(Context context) {
        if (api == null) {
            api = WebServiceApi.getInstance(context);
        }
    }

    public <T> T getResultObject(String url, Object paramObject,
                                 RequestMethod requestMathod, Object classOrTypeObject) {
        return (T) getResultObject(url, paramObject, requestMathod, false,
                classOrTypeObject);
    }

    private static void addFormField(Map<Object, Object> params,
                                     DataOutputStream output) {
        if ((params == null) || (params.size() == 0)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Object, Object> param : params.entrySet()) {
            try {
                String encodeValue = URLEncoder.encode(param.getValue()
                        .toString(), "utf-8");
                sb.append("--******\r\n");

                sb.append("Content-Disposition: form-data; name=\""
                        + param.getKey() + "\"" + "\r\n");
                sb.append("\r\n");
                sb.append(encodeValue + "\r\n");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            output.writeBytes(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUseLog() {
        return false;
    }

    public <T> T getResultObject(String url, Object paramObject,
                                 RequestObjectType requestObjectType, RequestMethod requestMathod,
                                 Object classOrTypeObject) {
        if (requestObjectType == RequestObjectType.JSON) {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("param", JsonManager.getInstance(
                    this.context).getJson(paramObject)));
            return (T) getResultObject(url, params, requestMathod,
                    classOrTypeObject);
        }
        if (requestObjectType == RequestObjectType.STRING) {
            List<NameValuePair> params = new ArrayList();
            if (paramObject != null) {
                params.add(new BasicNameValuePair("param", paramObject
                        .toString()));
            }
            return (T) getResultObject(url, params, requestMathod,
                    classOrTypeObject);
        }
        return (T) getResultObject(url, paramObject, requestMathod,
                classOrTypeObject);
    }

    public String post(String actionUrl, UploadData uploadData) {
        Log.e("http", "post Url:"+actionUrl);
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dataOutputStream = null;
        InputStream inputStream = null;
        String resultString = null;
        LogUtil.info(this,"actionUrl:", actionUrl);
        LogUtil.info(this,"uploadData:", uploadData);
        LogUtil.info(this,"sessionStr:", sessionStr);
        try {
            URL url = new URL(actionUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // httpURLConnection.setRequestProperty("Cookie",
            // "JSESSIONID=320C57C083E7F678ED14B8974732225E");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type",
                    UploadConstant.MULTIPART_FORM_DATA + ";boundary="
                            + UploadConstant.BOUNDARY);
            httpURLConnection.setChunkedStreamingMode(0);
            sessonInject(httpURLConnection);
            dataOutputStream = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            addFormField(uploadData.getParamMap(), dataOutputStream);
            if (!ListUtil.isEmpty(uploadData.getUploadFiles())) {
                addUploadDataContent(uploadData.getUploadFiles(),
                        dataOutputStream
                );
            }
            dataOutputStream.writeBytes(UploadConstant.LINEEND);
            dataOutputStream.writeBytes(UploadConstant.TWOHYPHENS
                    + UploadConstant.BOUNDARY + UploadConstant.TWOHYPHENS
                    + UploadConstant.LINEEND);
            dataOutputStream.flush();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            resultString = br.readLine();
            LogUtil.info("resultString", resultString);
            // LogUtil.systemOut(resultString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return resultString;

    }

    private String openRequest(String url, RequestMethod requestMethod,
                               Object paramObject) {

        if (!this.checkNetWork()) {
        }
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        String response = null;
        try {
            LogUtil.info(this, "Begin a request--->>>>>>>>>>");
            if (requestMethod == requestMethod.GET && paramObject != null) {
                url = url + "?" + this.encodeUrlParam(paramObject);
            }
            LogUtil.info(this, "url:", url);
            LogUtil.info(this, "requestMathod:", requestMethod);
            LogUtil.info(this, "paramObject:", paramObject);
            conn = (HttpURLConnection) new URL(url).openConnection();
            if (this.sessionStr != null) {
                conn.setRequestProperty("cookie", sessionStr);
            }
            conn.setConnectTimeout(5 * 1000);
            if (requestMethod == RequestMethod.GET) {
                conn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencode");
                conn.connect();
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencode");
                if (paramObject != null) {
                    conn.getOutputStream().write(
                            this.encodeUrlParam(paramObject).getBytes("GBK"));
                }
            }
            inputStream = conn.getInputStream();
            response = read(inputStream);
            LogUtil.info(this, "response:", response);
            if (this.sessionStr == null) {
                Map<String, List<String>> cookies = conn.getHeaderFields();
                for (String key : cookies.keySet()) {
                    if (key == null) {
                        continue;
                    }
                    if ("set-cookie".equals(key.toLowerCase())) {
                        List<String> values = cookies.get(key);
                        String sessionStrTemp = "";
                        for (String value : values) {
                            sessionStrTemp += value;
                            sessionStrTemp += ";";
                        }
                        this.sessionStr = sessionStrTemp;
                        this.sessionKey = key;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.info(this,
                    "End a request with exception below---XXXXXXXXXX");
            e.printStackTrace();
            return response;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        LogUtil.info(this, "End a request successfully---VVVVVVVVVV");
        return response;

    }

    private String read(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(in),
                    1024 * 4);
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader
                    .readLine()) {
                sb.append(line);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    private static void addUploadDataContent(List<UploadFile> uploadFiles,
                                             DataOutputStream dataOutputStream) {
        if (uploadFiles == null || uploadFiles.size() == 0) {
            return;
        }
        for (UploadFile uploadFile : uploadFiles) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(UploadConstant.TWOHYPHENS
                    + UploadConstant.BOUNDARY + UploadConstant.LINEEND);
            stringBuilder
                    .append("Content-Disposition: form-data;")
                    .append("name=\"" + uploadFile.getName() + "\";")
                    .append("filename=\""
                            + uploadFile.getFilePath()
                            .substring(
                                    uploadFile.getFilePath()
                                            .lastIndexOf("\\") + 1,
                                    uploadFile.getFilePath().length())
                            + "\";")
                    .append(UploadConstant.LINEEND)
                    .append("Content-Type:\"" + uploadFile.getContentType()
                            + "\"").append(UploadConstant.LINEEND)
                    .append(UploadConstant.LINEEND);
            FileInputStream fileInputStream = null;
            try {
                dataOutputStream.writeBytes(stringBuilder.toString());
                fileInputStream = new FileInputStream(uploadFile.getFilePath());
                byte[] buffer = new byte[102400]; // 8k
                int count = 0;
                while ((count = fileInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, count);
                }
                dataOutputStream.writeBytes(UploadConstant.LINEEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public <T> T getResultObject(String url, Object paramObject,
                                 RequestMethod requestMathod, boolean isMultipart,
                                 Object classOrTypeObject) {
        String responseString = null;
        if (isMultipart) {//false
            responseString = post(url, (UploadData) paramObject);
        } else {
            responseString = openRequest(url, requestMathod, paramObject);
            if (responseString == null) {
                return null;
            }
        }
        try {
            if (String.class.equals(classOrTypeObject)) {
                return (T) responseString;
            }

            return (T) getObject(responseString, (Type) classOrTypeObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getResultObject(String url, Object paramObject,
                                 Map<String, String> paramMap, RequestMethod requestMathod,
                                 boolean isMultipart, Object classOrTypeObject) {
        url = api.getUrl(url, paramMap);
        String responseString = null;
        if (isMultipart) {
            responseString = post(url, (UploadData) paramObject);
        } else {
            responseString = openRequest(url, requestMathod,

                    paramObject);
            if (responseString == null) {
                return null;
            }
        }
        try {
            if (String.class.equals(classOrTypeObject)) {
                return (T) responseString;
            }
            return (T) getObject(responseString, (Type) classOrTypeObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* Error */
    public android.graphics.Bitmap openRequestForBitmap(String url,
                                                        RequestMethod requestMethod, Object paramObject) {
        return null;
    }

    public byte[] getByteArray(String url, RequestMethod requestMethod,
                               Object paramObject, RequestListener fileDownloadListener) {
        if (!hasInternet(this.context)) {
            fileDownloadListener.onFail(RequestListener.RequestFailReason._NET);
            return null;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byte[] bytes = null;
            if (isUseLog()) {
                LogUtil.info(this, "Begin a request--->>>>>>>>>>");
            }
            if ((requestMethod == RequestMethod.GET) && (paramObject != null)) {
                url = url + "?" + encodeUrlParam(paramObject);
            }
            if (isUseLog()) {
                LogUtil.info(this, "url:", url);
                LogUtil.info(this, "requestMathod:", requestMethod);
                LogUtil.info(this, "paramObject:", paramObject);
            }
            httpURLConnection = (HttpURLConnection) new URL(url)
                    .openConnection();
            if (sessionStr != null) {
                httpURLConnection.setRequestProperty("cookie", sessionStr);
            }
            httpURLConnection.setConnectTimeout(5000);
            if (requestMethod == RequestMethod.GET) {
                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.setRequestProperty("Accept-Encoding",
                        "identity");

                httpURLConnection.connect();
            } else {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencode");
                if (paramObject != null) {
                    httpURLConnection.getOutputStream().write(
                            encodeUrlParam(paramObject).getBytes("utf-8"));
                }
            }
            double contentLength = httpURLConnection.getContentLength();

            inputStream = httpURLConnection.getInputStream();
            if (inputStream != null) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buf = new byte['?'];

                int read = -1;
                long downloadCount = 0L;
                while ((read = inputStream.read(buf)) != -1) {
                    byteArrayOutputStream.write(buf, 0, read);
                    downloadCount += read;
                    fileDownloadListener.onProgress(downloadCount,
                            contentLength);
                }
                bytes = byteArrayOutputStream.toByteArray();
            }
            if (isUseLog()) {
                LogUtil.info(this, "End a request successfully---VVVVVVVVVV");
                LogUtil.info(this, "response:", Boolean.valueOf(bytes == null));
            }
            fileDownloadListener.onSuccess(bytes);
            return bytes;
        } catch (Exception e) {
            if (isUseLog()) {
                LogUtil.info(this,
                        "End a request with exception below---XXXXXXXXXX");
            }
            fileDownloadListener
                    .onFail(RequestListener.RequestFailReason.EXCEPTION);
            e.printStackTrace();
            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            StreamUtil.closeStream(byteArrayOutputStream);
            StreamUtil.closeStream(inputStream);
        }
    }

    private void resolveSession(HttpURLConnection httpURLConnection) {
        if (sessionStr == null) {
            Map<String, List<String>> cookies = httpURLConnection
                    .getHeaderFields();
            for (String key : cookies.keySet()) {
                if (key != null) {
                    if ("set-cookie".equals(key.toLowerCase())) {
                        List<String> values = (List) cookies.get(key);
                        String sessionStrTemp = "";
                        for (String value : values) {
                            sessionStrTemp = sessionStrTemp + value;
                            sessionStrTemp = sessionStrTemp + ";";
                        }
                        sessionStr = sessionStrTemp;
                        sessionKey = key;
                        break;
                    }
                }
            }
        }
    }

    private String encodeUrlParam(Object paramObject)
            throws UnsupportedEncodingException {
        if (paramObject == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if ((paramObject instanceof Bundle)) {
            Bundle bundle = (Bundle) paramObject;
            if (bundle.size() == 0) {
                return "";
            }
            boolean isFirst = true;
            for (String key : bundle.keySet()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append("&");
                }
                sb.append(key + "="
                        + URLEncoder.encode(bundle.getString(key), "utf-8"));
            }
        } else {
            Object key;
            if ((paramObject instanceof Map)) {
                HashMap<String, String> map = (HashMap) paramObject;
                if (map.size() == 0) {
                    return "";
                }
                boolean isFirst = true;
                for (Iterator<String> iterator = map.keySet().iterator(); iterator
                        .hasNext();) {
                    key = iterator.next();
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        sb.append("&");
                    }
                    sb.append(key
                            + "="
                            + URLEncoder.encode(
                            ((String) map.get(key)).toString(), "utf-8"));
                }
            } else if (((paramObject instanceof List))
                    && ((((List) paramObject).get(0) instanceof NameValuePair))) {
                boolean isFirst = true;
                for (key = ((List) paramObject).iterator(); ((Iterator) key)
                        .hasNext();) {
                    NameValuePair nameValuePair = (NameValuePair) ((Iterator) key)
                            .next();
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        sb.append("&");
                    }
                    sb.append(nameValuePair.getName()
                            + "="
                            + URLEncoder.encode(nameValuePair.getValue(),
                            "utf-8"));
                }
            } else {
                sb.append("param=" +
                        URLEncoder.encode(jsonManager.getJson(paramObject), "utf-8"));
            }
        }
        return sb.toString();
    }

    public <T> T getObject(String jsonString, Type type) {
        return (T) jsonManager.getObject(jsonString, type);
    }

    private boolean checkNetWork() {
        boolean hasInternet = hasInternet(this.context);
        if (!hasInternet) {
            if (onNetResultListenerMain != null) {
                onNetResultListenerMain.onNetNotConnect();
            } else if (getOnNetResultListener() != null) {
                getOnNetResultListener().onNetNotConnect();
            }
            return false;
        }
        return true;
    }

    public static boolean hasInternet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if ((info == null) || (!info.isConnected())) {
            return false;
        }
        if (info.isRoaming()) {
            return true;
        }
        return true;
    }

    private void sessonInject(HttpRequest httpRequest) {
        Header header = cookieHeader;
        if (header != null) {
            httpRequest.addHeader(header);
        }
    }

    public static void sessonInject(HttpURLConnection httpURLConnection) {
        if (httpURLConnection == null) {
            return;
        }
        if (sessionKey == null) {
            return;
        }
        httpURLConnection.setRequestProperty(

                "Cookie",

                sessionStr);
    }

    private void sessonSet(HttpResponse httpResponse) {
        if (cookieHeader != null) {
            return;
        }
        Header[] headers = httpResponse.getAllHeaders();
        for (int i = 0; i < headers.length; i++) {
            Header header = headers[i];
            if ("set-cookie".equals(header.getName().toLowerCase())) {
                cookieHeader = header;

                break;
            }
        }
    }

    public String doGet(String url) {
        checkNetWork();

        HttpGet httpRequest = new HttpGet(url);

        sessonInject(httpRequest);
        String strResult = "doGetError";
        try {
            HttpResponse httpResponse = this.httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity(),
                        "UTF-8");
                sessonSet(httpResponse);
            } else {
                strResult = "false";
            }
        } catch (ClientProtocolException e) {
            strResult = "false";
            e.printStackTrace();
        } catch (IOException e) {
            strResult = "false";
            e.printStackTrace();
        } catch (Exception e) {
            strResult = "false";
            e.printStackTrace();
        }
        return strResult;
    }

    public String doPost(String url, String info) {
        checkNetWork();

        HttpPost httpRequest = new HttpPost(url);
        String strResult = "doPostError";
        try {
            StringEntity stringEntity = new StringEntity(info, "UTF-8");
            httpRequest.setEntity(stringEntity);
            sessonInject(httpRequest);

            HttpResponse httpResponse = this.httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity(),
                        "UTF-8");
                sessonSet(httpResponse);
            } else {
                strResult = "false";
            }
        } catch (ClientProtocolException e) {
            strResult = "false";
            e.printStackTrace();
        } catch (IOException e) {
            strResult = "false";
            e.printStackTrace();
        } catch (Exception e) {
            strResult = "false";
            e.printStackTrace();
        }
        return strResult;
    }

    public String doPost(String url, List<NameValuePair> params) {
        checkNetWork();

        HttpPost httpRequest = new HttpPost(url);
        String strResult = "doPostError";
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            sessonInject(httpRequest);

            HttpResponse httpResponse = this.httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity(),
                        "UTF-8");
            } else {
                strResult = "false";
            }
        } catch (ClientProtocolException e) {
            strResult = "false";
            e.printStackTrace();
        } catch (IOException e) {
            strResult = "false";
            e.printStackTrace();
        } catch (Exception e) {
            strResult = "false";
            e.printStackTrace();
        }
        return strResult;
    }

    public String loginPost(String url, List<NameValuePair> params) {
        checkNetWork();

        HttpPost httpRequest = new HttpPost(url);
        String strResult = "doPostError";
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse httpResponse = this.httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity(),
                        "UTF-8");
            } else {
                strResult = "false";
            }
        } catch (ClientProtocolException e) {
            strResult = "false";
            e.printStackTrace();
        } catch (IOException e) {
            strResult = "IOException";
            e.printStackTrace();
        } catch (Exception e) {
            strResult = "false";
            e.printStackTrace();
        }
        return strResult;
    }

    public HttpClient getHttpClient() {
        HttpParams httpParams = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
        HttpConnectionParams.setSoTimeout(httpParams, 60000);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);

        HttpClientParams.setRedirecting(httpParams, true);

        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
        HttpProtocolParams.setUserAgent(httpParams, userAgent);

        this.httpClient = new DefaultHttpClient(httpParams);
        return this.httpClient;
    }

    public OnNetResultListener getOnNetResultListener() {
        return this.onNetResultListener;
    }

    public void setOnNetResultListener(OnNetResultListener onNetResultListener) {
        this.onNetResultListener = onNetResultListener;
    }

    public static abstract interface HttpReqestProgressListener {
        public abstract void onRequestProgress(Float paramFloat);

        public abstract void onRequestFail();
    }

    public static abstract interface OnNetResultListener {
        public abstract void onNetNotConnect();
    }


}
