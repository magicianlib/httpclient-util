package io.magicianlib.interceptor;

import io.magicianlib.RequestConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 请求超时设置
 *
 * @author magicianlib@gmail.com
 */
public class TimeoutInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        RequestConfig config = request.tag(RequestConfig.class);
        if (Objects.nonNull(config)) {
            return chain.withConnectTimeout(config.getConnectTimeout(), TimeUnit.MILLISECONDS) // 连接超时
                    .withReadTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS) // 读超时
                    .withWriteTimeout(config.getWriteTimeout(), TimeUnit.MILLISECONDS) // 写超时
                    .proceed(request);
        }

        return chain.proceed(request);
    }
}
