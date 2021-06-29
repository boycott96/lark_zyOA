package lch.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages={"lch.**"})
@MapperScan({"lch.dao.**"})
@EnableScheduling
public class LarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(LarkApplication.class, args);
    }
}
