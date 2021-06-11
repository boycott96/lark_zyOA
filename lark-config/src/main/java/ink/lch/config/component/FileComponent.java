package ink.lch.config.component;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件转化
 * image <=> base64
 * 获取网站的icon图标等
 *
 * @author huaisun
 */
@Slf4j
@Component
public class FileComponent {

    /**
     * 将url 转成Blob编码npm 
     *
     * @param url 图片链接
     * @return Blob
     */
    public byte[] encodeImageToBlob(URL url) {
        log.info("图片的路径：" + url.toString());
        try {
            // 请求链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            InputStream is = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            // 写入流
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            // 转码
            return outStream.toByteArray();
        } catch (IOException e) {
            log.info("连接超时");
            return null;
        }
    }

    /**
     * base64转blob
     *
     * @param base64 64编码
     * @return blob
     */
    public byte[] encodeBase64ToBlob(String base64) {
        log.info("图片64位编码：" + base64);
        return Base64.decode(base64);
    }

    /**
     * 工具获取icon
     *
     * @param url 链接
     * @return blob格式
     */
    public byte[] getIcoUrl(String url) {
        if (url.length() - url.replaceAll("/", "").length() > 2) {
            // 说明含有子域名
            // 头部筛出
            String http = url.substring(0, url.indexOf("//") + 2);
            String suffixUrl = url.substring(url.indexOf("//") + 2);

            // 域名筛出
            String domain = suffixUrl.substring(0, suffixUrl.indexOf("/"));
            url = http + domain;
        }
        try {
            byte[] bytes = encodeImageToBlob(new URL(url + "/favicon.ico"));
            if (bytes == null) {
                bytes = encodeImageToBlob(new URL("http://www.lch.ink/favicon.ico"));
            }
            return bytes;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
