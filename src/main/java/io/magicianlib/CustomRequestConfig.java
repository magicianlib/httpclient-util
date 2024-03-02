package io.magicianlib;

import okhttp3.Headers;

import java.util.HashMap;
import java.util.Map;

public class CustomRequestConfig {
    /**
     * 是否打印请求日志
     */
    private boolean needLog;
    /**
     * 如果没有响应内容触发异常
     */
    private boolean fatalNoContent;
    /**
     * 设置代理地址 e.g. tcp://127.0.0.1:8080
     * <p>
     * 可选的代理协议如下：</p>
     * <ul>
     * <li>direct：表示直接连接或没有代理</li>
     * <li>http：表示高级协议(如HTTP或FTP)的代理</li>
     * <li>SOCKS：表示SOCKS (V4或V5)代理</li>
     * </ul>
     * <p>如果指定了一个不存在的代理协议，将默认选择 http</p>
     */
    private String proxy;
    /**
     * 失败重试次数
     */
    private int maxRetry;
    /**
     * 从服务器读取数据的超时时间
     */
    private int readTimeout = 10_000;
    /**
     * 向服务器写入数据的超时时间
     */
    private int writeTimeout = 10_000;

    private final Map<String, String> header = new HashMap<>();

    public void addHeader(String name, String value) {
        header.put(name, value);
    }

    public void removeHeader(String name) {
        header.remove(name);
    }

    public Headers toHeaders() {
        return Headers.of(header);
    }

    public boolean isNeedLog() {
        return needLog;
    }

    public void setNeedLog(boolean needLog) {
        this.needLog = needLog;
    }

    public boolean isFatalNoContent() {
        return fatalNoContent;
    }

    public void setFatalNoContent(boolean fatalNoContent) {
        this.fatalNoContent = fatalNoContent;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }
}
