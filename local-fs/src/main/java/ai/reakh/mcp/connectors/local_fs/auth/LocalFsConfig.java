package ai.reakh.mcp.connectors.local_fs.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import ai.reakh.mcp.sdk.UserMcpSdk;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class LocalFsConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("local-fs/i18n/label");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    public UserMcpSdk userMcpSdk() {
        return new LocalFsUserService();
    }

    @Value("${ai.reakh.mcp.connectors.local_fs.accessKey}")
    private String accessKey;

    @Value("${ai.reakh.mcp.connectors.local_fs.secretKey}")
    private String secretKey;
}
