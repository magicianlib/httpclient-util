package io.magicianlib.parameter;

import okhttp3.FormBody;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class QueryParameter {

    private final List<Part> parameters = new ArrayList<>();

    public void addParameter(String name, String value) {
        parameters.add(new Part(name, value));
    }

    protected static class Part {
        private final String name;
        private final String value;

        public Part(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    @Override
    public String toString() {
        StringJoiner builder = new StringJoiner("&");
        for (Part pair : parameters) {
            builder.add(pair.name + "=" + pair.value);
        }
        return builder.toString();
    }

    public String toUrl(String url) {
        if (parameters.size() == 0) {
            return url;
        }

        String query = toString();

        if (url.contains("?")) {
            return url + "&" + query;
        }

        return url + "?" + query;
    }

    public FormBody toForm() {
        FormBody.Builder builder = new FormBody.Builder();
        parameters.forEach(pair -> builder.add(pair.name, pair.value));
        return builder.build();
    }
}
