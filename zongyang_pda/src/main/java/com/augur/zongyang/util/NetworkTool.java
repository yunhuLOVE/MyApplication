package com.augur.zongyang.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by yunhu on 2018-01-18.
 */

public class NetworkTool {

    /**
     * 获取网址内容
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String getContent( String url) throws Exception {
        StringBuilder sb = new StringBuilder();
        // TODO Auto-generated method stub
        HttpClient client = new DefaultHttpClient();
        HttpParams httpParams = client.getParams();
        // 设置网络超时参数
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
        HttpResponse response;
        response = client.execute(new HttpGet(url));
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    entity.getContent(), "GB2312"), 8192);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
        }

        return sb.toString();
    }

}
