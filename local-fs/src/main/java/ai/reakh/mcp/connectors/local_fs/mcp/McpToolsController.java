package ai.reakh.mcp.connectors.local_fs.mcp;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ai.reakh.mcp.connectors.local_fs.WebConfigurer;
import ai.reakh.mcp.sdk.mcp.McpControllerHelper;
import ai.reakh.mcp.sdk.mcp.model.McpProtocolBase;
import ai.reakh.mcp.sdk.mcp.model.request.McpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wanshao create time is 2019/12/31 6:02 下午
 **/
@RestController
@RequestMapping(WebConfigurer.MCP_URI)
@Slf4j
public class McpToolsController {

    @Resource
    private McpControllerHelper helper;

    @PostConstruct
    public void init() {
        helper.initTools();
    }

    @RequestMapping(value = "/localfs", method = { RequestMethod.POST, RequestMethod.GET })
    public McpProtocolBase handle(@RequestBody @Valid McpRequest req, HttpServletRequest request) {
        return helper.handleRequest(req, request);
    }
}
