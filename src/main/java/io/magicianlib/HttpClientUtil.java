package io.magicianlib;

import io.magicianlib.method.GET;
import io.magicianlib.method.POST;
import io.magicianlib.parameter.MultipartParameter;
import io.magicianlib.parameter.QueryParameter;

import java.io.File;

public final class HttpClientUtil {
    private static final QueryParameter DEFAULT_QUERY = new QueryParameter();
    private static final CustomRequestConfig DEFAULT_CONFIG = new CustomRequestConfig();

    public static String get(String url) {
        return get(url, DEFAULT_QUERY);
    }

    public static String get(String url, QueryParameter parameter) {
        return get(url, parameter, DEFAULT_CONFIG);
    }

    public static String get(String url, QueryParameter parameter, CustomRequestConfig config) {
        return GET.get(url, parameter, config);
    }

    /**
     * get file with byte[]
     */
    public static byte[] getFile(String url) {
        return getFile(url, DEFAULT_QUERY);
    }

    public static byte[] getFile(String url, QueryParameter parameter) {
        return getFile(url, parameter, DEFAULT_CONFIG);
    }

    public static byte[] getFile(String url, QueryParameter parameter, CustomRequestConfig config) {
        return GET.getFile(url, parameter, config);
    }

    /**
     * @return download file path
     */
    public static String download(String url) {
        return download(url, DEFAULT_QUERY);
    }

    public static String download(String url, QueryParameter parameter) {
        return download(url, parameter, DEFAULT_CONFIG);
    }

    public static String download(String url, QueryParameter parameter, CustomRequestConfig config) {
        return GET.download(url, parameter, config);
    }

    public static String postText(String url, String text) {
        return postText(url, text, DEFAULT_CONFIG);
    }

    public static String postText(String url, String text, CustomRequestConfig config) {
        return POST.postText(url, text, config);
    }

    public static String postJson(String url, String json) {
        return postJson(url, json, DEFAULT_CONFIG);
    }

    public static String postJson(String url, String json, CustomRequestConfig config) {
        return POST.postJson(url, json, config);
    }

    public static String postForm(String url, QueryParameter parameter) {
        return postForm(url, parameter, DEFAULT_CONFIG);
    }

    public static String postForm(String url, QueryParameter parameter, CustomRequestConfig config) {
        return POST.postForm(url, parameter, config);
    }

    public static String postMultipart(String url, MultipartParameter parameter) {
        return postMultipart(url, parameter, DEFAULT_CONFIG);
    }

    public static String postMultipart(String url, MultipartParameter parameter, CustomRequestConfig config) {
        return POST.postMultipart(url, parameter, config);
    }

    public static String postFile(String url, File file) {
        return postFile(url, file, DEFAULT_CONFIG);
    }

    public static String postFile(String url, File file, CustomRequestConfig config) {
        return POST.postFile(url, file, config);
    }

    public static String postFile(String url, String fileName, byte[] file) {
        return postFile(url, fileName, file, DEFAULT_CONFIG);
    }

    public static String postFile(String url, String fileName, byte[] file, CustomRequestConfig config) {
        return POST.postFile(url, fileName, file, config);
    }
}
