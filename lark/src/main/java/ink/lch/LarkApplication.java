package ink.lch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"ink.lch.**"})
@MapperScan({"ink.lch.dao.**"})
public class LarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(LarkApplication.class, args);
    }
}
