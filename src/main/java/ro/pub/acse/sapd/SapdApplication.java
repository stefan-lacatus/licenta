package ro.pub.acse.sapd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SapdApplication {
    public static String EVENT_LOGGER = "AlegriaEventLogger";

    public static void main(String[] args) {
        SpringApplication.run(SapdApplication.class, args);
    }
}
