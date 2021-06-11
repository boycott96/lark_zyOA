package ink.lch.config.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 爬取网页
 */
@Slf4j
public class WebContent {

    /**
     * 读取一个网页全部内容
     *
     * @param htmlUrl 参数
     * @return 结果
     * @throws IOException 异常
     */
    public static String getOneHtml(final String htmlUrl) throws IOException {
        URL url;
        String temp;
        final StringBuilder sb = new StringBuilder();
        try {
            url = new URL(htmlUrl);
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));// 读取网页全部内容
            while ((temp = in.readLine()) != null) {
                sb.append(temp);
            }
            in.close();
        } catch (final MalformedURLException me) {
            log.info("你输入的URL格式有问题！请仔细输入");
            me.getMessage();
            throw me;
        } catch (final IOException e) {
            e.printStackTrace();
            throw e;
        }
        return sb.toString();
    }

    /**
     * @param s 参数
     * @return 获得网页标题
     */
    public static String getTitle(final String s) {
        StringBuilder title = new StringBuilder();
        final List<String> list = new ArrayList<>();
        String regex = "<title>.*?</title>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(s);
        while (ma.find()) {
            list.add(ma.group());
        }
        for (String value : list) {
            title.append(value);
        }
        return outTag(title.toString());
    }

    /**
     * @param s 参数
     * @return 去掉标记
     */
    public static String outTag(final String s) {
        return s.replaceAll("<.*?>", "");
    }

    /**
     * @param s 参数
     * @return 获取标题和图标
     */
    public static String getWebTitle(final String s) {
        String html = "";
        log.info("------------------开始读取网页(" + s + ")--------------------");
        try {
            html = getOneHtml(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("------------------读取网页(" + s + ")结束--------------------");
        return getTitle(html);
    }
}  