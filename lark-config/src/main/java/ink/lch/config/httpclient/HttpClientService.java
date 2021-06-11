package ink.lch.config.httpclient;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@Service
@Slf4j
public class HttpClientService {

    private final CloseableHttpClient httpClient;

    private final RequestConfig requestConfig;

    @Autowired
    public HttpClientService(CloseableHttpClient httpClient, RequestConfig requestConfig) {
        this.httpClient = httpClient;
        this.requestConfig = requestConfig;
    }

    /**
     * GET 请求，指定url 和头部信息，进行http请求，获取参数。并判断执行是否成功，如果未成功，则进行打印，并返回空
     *
     * @param url     请求API接口
     * @param headers 头部信息
     * @return CloseableHttpResponse返回接口回馈实体信息
     * @throws IOException 抛出异常
     */
    public CloseableHttpResponse doGet(String url, Header[] headers) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeaders(headers);
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() != 200) {
            log.info("返回状态异常：" + EntityUtils.toString(response.getEntity(), "UTF-8"));
        }
        return response;
    }

    /**
     * 重载有参数的GET请求，指定url、头部信息、参数信息，进行http请求
     *
     * @param url     请求API
     * @param headers 头部信息
     * @param map     参数信息
     * @return CloseableHttpResponse返回接口回馈实体信息
     * @throws URISyntaxException URI转换异常
     * @throws IOException        请求异常
     */
    public CloseableHttpResponse doGet(String url, Header[] headers, Map<String, Object> map) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        return this.doGet(uriBuilder.build().toString(), headers);
    }

    /**
     * 有参数的POST请求，指定url、头部信息、参数信息，进行post请求
     * @param url 请求API
     * @param headers 头部信息
     * @param map 参数信息
     * @return CloseableHttpResponse返回接口回馈实体信息
     * @throws IOException 请求异常
     */
    public CloseableHttpResponse doPost(String url, Header[] headers, Map<String, Object> map) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(headers);
        httpPost.setConfig(requestConfig);

        if (map != null) {
            String str = JSONObject.toJSONString(map);
            StringEntity stringEntity = new StringEntity(str, "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        }
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() != 200) {
            log.info("返回状态异常：" + EntityUtils.toString(response.getEntity(), "UTF-8"));
        }
        return response;
    }

    /**
     * PATCH请求，指定url、header、map
     * @param url api接口
     * @param headers 头部信息
     * @param map 参数信息
     * @return CloseableHttpResponse返回接口回馈实体信息
     * @throws IOException
     */
    public CloseableHttpResponse doPatch(String url, Header[] headers, Map<String, Object> map) throws IOException {
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setHeaders(headers);
        httpPatch.setConfig(requestConfig);

        if (map != null) {
            String str = JSONObject.toJSONString(map);
            StringEntity stringEntity = new StringEntity(str, "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPatch.setEntity(stringEntity);
        }

        CloseableHttpResponse response = this.httpClient.execute(httpPatch);
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() != 200) {
            log.info("返回状态异常：" + EntityUtils.toString(response.getEntity(), "UTF-8"));
        }
        return response;
    }

    /**
     * DELETE请求，指定URL、Header
     * @param url API接口
     * @param headers 头部信息
     * @return
     * @throws IOException
     */
    public CloseableHttpResponse doDelete(String url, Header[] headers) throws IOException {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeaders(headers);
        httpDelete.setConfig(requestConfig);

        CloseableHttpResponse response = this.httpClient.execute(httpDelete);
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() != 200) {
            log.info("返回状态异常：" + EntityUtils.toString(response.getEntity(), "UTF-8"));
        }
        return response;
    }
}
