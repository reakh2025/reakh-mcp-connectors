package ai.reakh.mcp.connectors.local_fs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * @author bucketli 2023/10/25 19:31:23
 */
@SpringBootApplication()
@ComponentScan({"ai.reakh.mcp.*"})
@Slf4j
public class LocalFsMcpServerLauncher {

    public static void main(String[] args) throws Exception {
        System.setProperty("spring.config.name", "application");

        // loader
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

        // spring
        SpringApplication application = new SpringApplication(resourceLoader, LocalFsMcpServerLauncher.class);
        ConfigurableApplicationContext context = application.run(args);

        log.info("ClouGenceRdpLauncher Started.");
    }
}
