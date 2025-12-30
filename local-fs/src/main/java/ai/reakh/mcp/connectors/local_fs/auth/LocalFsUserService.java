package ai.reakh.mcp.connectors.local_fs.auth;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.MessageSource;

import ai.reakh.mcp.connectors.local_fs.WebConfigurer;
import ai.reakh.mcp.sdk.UserInfo;
import ai.reakh.mcp.sdk.UserMcpSdk;
import ai.reakh.mcp.sdk.mcp.McpI18nProxy;
import ai.reakh.mcp.sdk.openapi.OpenApiSigner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalFsUserService implements UserMcpSdk {

    @Resource
    private LocalFsConfig config;

    @Resource
    private MessageSource messageSource;

    @PostConstruct
    public void printMcpUrl() {
        String nonce = "" + System.currentTimeMillis();
        Map<String, String> paramToSign = new HashMap<>();
        paramToSign.put("SignatureMethod", "HmacSHA1");
        paramToSign.put("SignatureNonce", nonce);
        paramToSign.put("AccessKeyId", config.getAccessKey());
        String str = OpenApiSigner.composeStringToSign(paramToSign);
        String re = OpenApiSigner.signString(str, config.getSecretKey());

        String mcpURL = "http://127.0.0.1:" + config.getAppPort() + WebConfigurer.MCP_URI + "?AccessKeyId=" + paramToSign.get("AccessKeyId") + "&Signature=" + URLEncoder.encode(re)
                        + "&SignatureMethod=HmacSHA1&SignatureNonce=" + nonce;

        log.info("[MCP SERVICE]:" + mcpURL);
    }

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
    public McpI18nProxy getI18nProxy() { return key -> messageSource.getMessage(key, null, Locale.getDefault()); }
}
