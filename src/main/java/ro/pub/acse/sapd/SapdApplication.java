package ro.pub.acse.sapd;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJSONDoc
public class SapdApplication {

    public static void main(String[] args) {
        SpringApplication.run(SapdApplication.class, args);
    }
}
