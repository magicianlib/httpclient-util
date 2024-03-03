package io.magicianlib.interceptor;

import io.magicianlib.RequestConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

/**
 * 失败重试
 *
 * @author magicianlib@gmail.com
 */
public class RetryInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        RequestConfig config = request.tag(RequestConfig.class);
        if (Objects.nonNull(config)) {
            int retry = config.getRetry();
            while (--retry >= 0) {
                try {
                    return chain.proceed(request);
                } catch (IOException e) {
                    // ignore exception
                }
            }
        }

        return chain.proceed(request);
    }
}
