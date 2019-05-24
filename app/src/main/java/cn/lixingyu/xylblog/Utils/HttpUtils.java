package cn.lixingyu.xylblog.Utils;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author lxxxxxxy
 * @time 2019/5/20 08:21
 */
public class HttpUtils {
    public static void sendOkHttpRequestByGet(String url, Callback callback) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestByPost(String url, String requestName, String requestBody, Callback callback) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody res = new FormBody.Builder().add(requestName, requestBody).build();
        Request request = new Request.Builder().url(url).post(res).build();
        client.newCall(request).enqueue(callback);
    }
}
