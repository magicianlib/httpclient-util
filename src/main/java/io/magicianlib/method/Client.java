package io.magicianlib.method;

import io.magicianlib.HttpException;
import io.magicianlib.interceptor.RetryInterceptor;
import io.magicianlib.interceptor.TimeoutInterceptor;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Client {
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .addInterceptor(new TimeoutInterceptor())
            .addInterceptor(new RetryInterceptor())
            .build();

    private static final Pattern PROXY_PATTERN = Pattern.compile("^(?<protocol>\\w+):/+(?<hostname>[\\w.]+):(?<port>\\d+)$");

    protected static Call call(Request request) {
        return selector(request).newCall(request);
    }

    private static OkHttpClient selector(Request request) {
        // 如果指定了代理服务器地址则创建一个新的 client
        String proxy = request.header("Proxy");
        if (proxy == null || proxy.length() == 0) {
            return CLIENT;
        }

        // 设置代理服务器地址
        Matcher matcher = PROXY_PATTERN.matcher(proxy);
        if (matcher.matches()) {
            String protocol = matcher.group("protocol");
            String hostname = matcher.group("hostname");
            String port = matcher.group("port");

            Proxy.Type proxyType = Proxy.Type.HTTP;
            for (Proxy.Type type : Proxy.Type.values()) {
                if (type.name().equalsIgnoreCase(protocol)) {
                    proxyType = type;
                    break;
                }
            }

            // 创建新 client 实例
            return CLIENT.newBuilder().proxy(new Proxy(proxyType, new InetSocketAddress(hostname, Integer.parseInt(port)))).build();
        }

        // 如果代理服务器地址非法则抛出异常
        throw new HttpException("Illegal proxy address: " + proxy + ". Example of a correct proxy address: tcp://hostname:port");
    }
}
