package ai.reakh.mcp.connectors.local_fs.auth;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import ai.reakh.mcp.sdk.UserInfo;
import ai.reakh.mcp.sdk.UserMcpSdk;
import ai.reakh.mcp.sdk.mcp.McpI18nProxy;

@Component
public class LocalFsUserService implements UserMcpSdk {

    @Resource
    private LocalFsConfig config;

    @Resource
    private MessageSource messageSource;

    @Override
    public UserInfo fetchByAccessKey(String accessKey) {
        if (config.getAccessKey() == null || config.getSecretKey() == null) {
            throw new IllegalArgumentException("Need to set a pair of accessKey and secretKye in system.");
        }

        if (accessKey == null) {
            return null;
        }

        if (!config.getAccessKey().equals(accessKey)) {
            return null;
        }

        UserInfo i = new UserInfo();
        i.setSecretKey(config.getSecretKey());
        return i;
    }

    @Override
    public McpI18nProxy getI18nProxy() {
        return key -> messageSource.getMessage(key, null, Locale.getDefault());
    }
}
