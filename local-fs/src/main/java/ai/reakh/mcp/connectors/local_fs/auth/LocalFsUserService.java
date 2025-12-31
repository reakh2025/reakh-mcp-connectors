package ai.reakh.mcp.connectors.local_fs.auth;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;

import ai.reakh.mcp.connectors.local_fs.WebConfigurer;
import ai.reakh.mcp.sdk.UserInfo;
import ai.reakh.mcp.sdk.UserMcpSdk;
import ai.reakh.mcp.sdk.mcp.McpI18nProxy;
import ai.reakh.mcp.sdk.openapi.OpenApiSigner;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalFsUserService implements UserMcpSdk {

    private final LocalFsConfig localFsConfig;

    private final MessageSource messageSource;

    public LocalFsUserService(LocalFsConfig localFsConfig, MessageSource messageSource){
        this.localFsConfig = localFsConfig;
        this.messageSource = messageSource;
    }

    public void printMcpUrl() {
        String nonce = "" + System.currentTimeMillis();
        Map<String, String> paramToSign = new HashMap<>();
        paramToSign.put("SignatureMethod", "HmacSHA1");
        paramToSign.put("SignatureNonce", nonce);
        paramToSign.put("AccessKeyId", localFsConfig.getAccessKey());
        String str = OpenApiSigner.composeStringToSign(paramToSign);
        String re = OpenApiSigner.signString(str, localFsConfig.getSecretKey());

        String mcpURL = "http://127.0.0.1:" + localFsConfig.getAppPort() + WebConfigurer.MCP_URI + "?AccessKeyId=" + paramToSign.get("AccessKeyId") + "&Signature="
                        + URLEncoder.encode(re) + "&SignatureMethod=HmacSHA1&SignatureNonce=" + nonce;

        log.info("[MCP SERVICE]:" + mcpURL);
    }

    @Override
    public UserInfo fetchByAccessKey(String accessKey) {
        if (localFsConfig.getAccessKey() == null || localFsConfig.getSecretKey() == null) {
            throw new IllegalArgumentException("Need to set a pair of accessKey and secretKye in system.");
        }

        if (accessKey == null) {
            return null;
        }

        if (!localFsConfig.getAccessKey().equals(accessKey)) {
            return null;
        }

        UserInfo i = new UserInfo();
        i.setSecretKey(localFsConfig.getSecretKey());
        return i;
    }

    @Override
    public McpI18nProxy getI18nProxy() { return key -> messageSource.getMessage(key, null, Locale.getDefault()); }
}
