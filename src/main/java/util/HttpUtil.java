package util;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;

public class HttpUtil {
    private static final OkHttpClient okHttpClient = new OkHttpClient();


    public static String sendHttpRequest(int start, int end) throws IOException {
        Request request = new Request.Builder()
                .url(new StringBuilder()
                        .append(ConfigUtil.getApiConfig().getUrl())
                        .append("/" + URLEncoder.encode(ConfigUtil.getApiConfig().getKey(), ConfigUtil.getApiConfig().getEncoding()))
                        .append("/" + URLEncoder.encode(ConfigUtil.getApiConfig().getType(), ConfigUtil.getApiConfig().getEncoding()))
                        .append("/" + URLEncoder.encode(ConfigUtil.getApiConfig().getName(), ConfigUtil.getApiConfig().getEncoding()))
                        .append("/" + URLEncoder.encode(String.valueOf(start), ConfigUtil.getApiConfig().getEncoding()))
                        .append("/" + URLEncoder.encode(String.valueOf(end), ConfigUtil.getApiConfig().getEncoding()))
                        .toString())
                .addHeader("Content-type", "application/json")
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }
}
