package io.magicianlib.interceptor;

import io.magicianlib.CustomRequestConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class RetryInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        CustomRequestConfig config = request.tag(CustomRequestConfig.class);
        if (Objects.nonNull(config)) {
            int maxRetry = config.getMaxRetry();
            while (maxRetry >= 0) {
                try {
                    return chain.proceed(request);
                } catch (IOException e) {
                    // ignore IOException
                    --maxRetry;
                }
            }
        }

        return chain.proceed(request);
    }
}
