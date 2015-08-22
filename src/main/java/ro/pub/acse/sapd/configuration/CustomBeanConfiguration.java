package ro.pub.acse.sapd.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.pub.acse.sapd.blocks.BlockExecutor;
import ro.pub.acse.sapd.data.DataService;
import ro.pub.acse.sapd.diagrams.executor.DiagramExecutor;

@Configuration
public class CustomBeanConfiguration {
    @Bean
    public BlockExecutor blockExecutor() {
        return new BlockExecutor();
    }

    @Bean
    public DiagramExecutor diagramExecutorExecutor() {
        return new DiagramExecutor();
    }

    @Bean
    public DataService dataService() {
        return new DataService();
    }
}