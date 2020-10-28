package org.xyx.gateway.restful;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * description
 *
 * @author xyx
 * @date 2020/10/28 17:53
 */
public class HttpClientTest {


    public static Object test() {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        Call call = httpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
