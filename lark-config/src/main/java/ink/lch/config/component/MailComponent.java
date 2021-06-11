package ink.lch.config.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class MailComponent {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailComponent(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String from;

    static {
        initEmail();
        System.setProperty("mail.mime.splitlongparameters", "false");
    }

    public static String getHtml(String code) {
        String html = System.getProperty("emailHtml");
        html = html.replace("$(code)", code);
        return html;
    }

    public static void initEmail() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("static/email.html");
            InputStream is = classPathResource.getInputStream();
            InputStreamReader read = new InputStreamReader(is);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            StringBuilder sb = new StringBuilder();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                sb.append(lineTxt);
            }
            read.close();
            System.setProperty("emailHtml", sb.toString());

        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件-邮件正文是HTML
     */
    @Async
    public void sendMailHtml(String email, String title, String code) throws Exception {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(from);
        helper.setTo(email);
        helper.setSubject(title);
        String sb = getHtml(code);
        helper.setText(sb, true);
        javaMailSender.send(mimeMessage);
    }
}