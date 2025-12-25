package ai.reakh.mcp.connectors.local_fs;

import ai.reakh.mcp.sdk.mcp.McpSessionManager;
import ai.reakh.mcp.sdk.openapi.OpenApiSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    public static final String OPENAPI_URI = "/local_fs/macos/api";

    public static final String MCP_URI     = "/local_fs/macos/mcp";

    @Bean
    public OpenApiSessionManager apiSessionManager() {
        return new OpenApiSessionManager(OPENAPI_URI);
    }

    @Bean
    public McpSessionManager mcpSessionManager() {
        return new McpSessionManager(MCP_URI);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiSessionManager());
        registry.addInterceptor(mcpSessionManager());
    }
}
