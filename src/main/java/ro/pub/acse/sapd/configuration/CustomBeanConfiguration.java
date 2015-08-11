package ro.pub.acse.sapd.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.pub.acse.sapd.blocks.BlockExecutor;

@Configuration
public class CustomBeanConfiguration {
    @Bean
    public BlockExecutor blockExecutor() {
        return new BlockExecutor();
    }
}