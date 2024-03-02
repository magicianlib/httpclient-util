package io.magicianlib.interceptor;

import io.magicianlib.CustomRequestConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TimeoutInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        CustomRequestConfig config = request.tag(CustomRequestConfig.class);
        if (Objects.nonNull(config)) {
            return chain.withReadTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS)
                    .withConnectTimeout(config.getWriteTimeout(), TimeUnit.MILLISECONDS)
                    .proceed(request);
        }

        return chain.proceed(request);
    }
}
