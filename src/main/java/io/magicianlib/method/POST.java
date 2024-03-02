package io.magicianlib.method;

import io.magicianlib.CustomRequestConfig;
import io.magicianlib.HttpException;
import io.magicianlib.parameter.MultipartParameter;
import io.magicianlib.parameter.QueryParameter;
import okhttp3.*;
import okio.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public final class POST {
    private static final Logger LOGGER = LoggerFactory.getLogger(POST.class);

    public static String postText(String url, String text, CustomRequestConfig config) {
        RequestBody body = RequestBody.create(ByteString.encodeString(text, StandardCharsets.UTF_8), MediaType.parse("text/plain"));
        return doPost(url, body, config, request -> LOGGER.info("Http {} Body [{}]", request, text));
    }

    public static String postJson(String url, String json, CustomRequestConfig config) {
        return doPost(url, RequestBody.create(json, MediaType.parse("application/json; charset=utf-8")), config, request -> LOGGER.info("Http {} Body [{}]", request, json));
    }

    public static String postForm(String url, QueryParameter parameter, CustomRequestConfig config) {
        return doPost(url, parameter.toForm(), config, request -> LOGGER.info("Http {} Query [{}]", request, parameter));
    }

    public static String postMultipart(String url, MultipartParameter parameter, CustomRequestConfig config) {
        return doPost(url, parameter.toMultipart(), config, request -> LOGGER.info("Http {} Body [Multipart]", request));
    }

    public static String postFile(String url, File file, CustomRequestConfig config) {
        String fileName = file.getName();
        String type = URLConnection.guessContentTypeFromName(fileName);
        if (type == null) {
            type = "application/octet-stream"; // 默认二进制流
        }

        return doPost(url, RequestBody.create(file, MediaType.parse(type)), config, request -> LOGGER.info("Http {} Body [File={}]", request, file.getPath()));
    }

    public static String postFile(String url, String fileName, byte[] file, CustomRequestConfig config) {
        String type = URLConnection.guessContentTypeFromName(fileName);
        if (type == null) {
            type = "application/octet-stream"; // 默认二进制流
        }

        return doPost(url, RequestBody.create(file, MediaType.parse(type)), config, request -> LOGGER.info("Http {} Body [FileName={}, Data=bytes]", request, fileName));
    }

    public static String doPost(String url, RequestBody body, CustomRequestConfig config, Consumer<Request> printLog) {
        Request request = new Request.Builder()
                .headers(config.toHeaders())
                .header("Proxy", config.getProxy())
                .url(url)
                .tag(CustomRequestConfig.class, config)
                .post(body)
                .build();

        if (config.isNeedLog()) {
            printLog.accept(request);
        }

        Call call = Client.call(request);
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    return responseBody.string();

                }
            }

            if (config.isNeedLog()) {
                LOGGER.info("Request fail or no-content[code={}]", response.code());
            }
            if (config.isFatalNoContent()) {
                throw new HttpException("http request fail or no-content: " + response.code());
            }
        } catch (Exception e) {
            throw new HttpException(e);
        }

        return null;
    }
}
