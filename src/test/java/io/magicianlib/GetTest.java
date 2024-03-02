package io.magicianlib;

import io.magicianlib.parameter.QueryParameter;
import junit.framework.TestCase;

public class GetTest extends TestCase {

    public void testGet() {
        String s = HttpClientUtil.get("https://www.google.com/search?q=zheshi");
        System.out.println("content: \n" + s);
    }

    public void testGetWithQueryParameter() {
        QueryParameter parameter = new QueryParameter();
        parameter.addParameter("q", "中国");
        String s = HttpClientUtil.get("https://www.google.com/search", parameter);
        System.out.println("content: \n" + s);
    }

    public void testGetWithProxy() {
        QueryParameter parameter = new QueryParameter();
        parameter.addParameter("q", "中国");

        CustomRequestConfig config = new CustomRequestConfig();
        config.setProxy("tcp://127.0.0.1:7890");

        String s = HttpClientUtil.get("https://www.google.com/search", parameter, config);
        System.out.println("content: \n" + s);
    }

    public void testGetWithRetry() {
        QueryParameter parameter = new QueryParameter();
        parameter.addParameter("q", "中国");

        CustomRequestConfig config = new CustomRequestConfig();
        config.setMaxRetry(3);

        String s = HttpClientUtil.get("https://www.google.com/search", parameter, config);
        System.out.println("content: \n" + s);
    }

    public void testGetFile() {
        String url = "https://assets.website-files.com/5d5e2ff58f10c53dcffd8683/5d5e30ba8983564552c60dc7_selfie.svg";
        byte[] file = HttpClientUtil.getFile(url);
        System.out.println(file.length);
    }

    public void testDownload() {
        String url = "https://assets.website-files.com/5d5e2ff58f10c53dcffd8683/5d5e30ba8983564552c60dc7_selfie.svg";
        String filename = HttpClientUtil.download(url);
        System.out.println("download at:\n" + filename);
    }
}
