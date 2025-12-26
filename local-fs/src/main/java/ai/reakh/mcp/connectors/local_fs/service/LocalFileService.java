package ai.reakh.mcp.connectors.local_fs.service;

import java.util.List;

import ai.reakh.mcp.connectors.local_fs.model.vo.FileOrDirMetaVO;

public interface LocalFileService {

    List<FileOrDirMetaVO> listFileMetas(String targetDir, Integer maxDepth);

    List<String> grep(String targetFileOrDir, boolean isDirectory, String regex, String include, String exclude);
}
