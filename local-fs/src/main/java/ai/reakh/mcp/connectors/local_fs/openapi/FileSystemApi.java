package ai.reakh.mcp.connectors.local_fs.openapi;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ai.reakh.mcp.connectors.local_fs.WebConfigurer;
import ai.reakh.mcp.connectors.local_fs.commons.ResApiData;
import ai.reakh.mcp.connectors.local_fs.commons.ResApiDataUtils;
import ai.reakh.mcp.connectors.local_fs.mcp.McpLabel;
import ai.reakh.mcp.connectors.local_fs.model.fo.ListCurrDirFilesAndSubDirsFO;
import ai.reakh.mcp.connectors.local_fs.model.fo.ReadFileToTextFO;
import ai.reakh.mcp.connectors.local_fs.model.vo.FileOrDirMetaVO;
import ai.reakh.mcp.connectors.local_fs.model.vo.OsInfo;
import ai.reakh.mcp.connectors.local_fs.service.LocalFileService;
import ai.reakh.mcp.sdk.annotation.McpApiProvider;
import ai.reakh.mcp.sdk.annotation.McpTool;
import ai.reakh.mcp.sdk.openapi.OpenApiSessionManager;
import lombok.extern.slf4j.Slf4j;

@McpApiProvider
@RestController
@RequestMapping(value = WebConfigurer.OPENAPI_URI + "/fs")
@Slf4j
public class FileSystemApi {

    @Resource
    private LocalFileService localFileService;

    @McpTool(value = McpLabel.GET_OS_INFO)
    @RequestMapping(value = "/getOsInfo", method = RequestMethod.POST)
    public ResApiData<?> getOsInfo(HttpServletRequest request) {
        String requestId = (String) request.getAttribute(OpenApiSessionManager.OPEN_API_REQUEST_ID);
        log.info("getOsInfo for open api request id :" + requestId);

        OsInfo i = new OsInfo();
        i.convertFromSysProp();
        return ResApiDataUtils.buildSuccess(i);
    }

    @McpTool(value = McpLabel.LIST_CURR_DIR_FILES_AND_SUB_DIRS)
    @RequestMapping(value = "/listCurrDirFilesAndSubDirs", method = RequestMethod.POST)
    public ResApiData<?> listCurrDirFilesAndSubDirs(@RequestBody @Valid ListCurrDirFilesAndSubDirsFO fo, HttpServletRequest request) {
        String requestId = (String) request.getAttribute(OpenApiSessionManager.OPEN_API_REQUEST_ID);
        log.info("listCurrDirFilesAndSubDirs for open api request id :" + requestId);

        List<FileOrDirMetaVO> re = localFileService.listFileMetas(fo.getDirPath(), fo.getMaxDepth());
        return ResApiDataUtils.buildSuccess(re);
    }

    @McpTool(value = McpLabel.READ_FILE_TO_TEXT)
    @RequestMapping(value = "/readFileToText", method = RequestMethod.POST)
    public ResApiData<?> readFileToText(@RequestBody @Valid ReadFileToTextFO fo, HttpServletRequest request) {
        String requestId = (String) request.getAttribute(OpenApiSessionManager.OPEN_API_REQUEST_ID);
        log.info("readFileToText for open api request id :" + requestId);

        try {
            File f = new File(fo.getFilePath());
            String str = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
            return ResApiDataUtils.buildSuccess(str);
        } catch (Exception e) {
            String msg = "Read " + fo.getFilePath() + " 's content failed,msg:" + ExceptionUtils.getRootCauseMessage(e);
            log.error(msg, e);
            return ResApiDataUtils.buildError(msg);
        }
    }
}
