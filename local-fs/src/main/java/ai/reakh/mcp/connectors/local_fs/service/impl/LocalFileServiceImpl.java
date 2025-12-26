package ai.reakh.mcp.connectors.local_fs.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import ai.reakh.mcp.connectors.local_fs.model.vo.FileOrDirMetaVO;
import ai.reakh.mcp.connectors.local_fs.service.LocalFileService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LocalFileServiceImpl implements LocalFileService {

    @Override
    public List<FileOrDirMetaVO> listFileMetas(String targetDir, Integer maxDepth) {
        if (maxDepth == null || maxDepth < 1) {
            maxDepth = 1;
        }

        if (maxDepth > 3) {
            throw new IllegalArgumentException("The max depth can not be large than 3.");
        }

        List<FileOrDirMetaVO> re = new ArrayList<>();
        try (Stream<Path> s = Files.walk(Paths.get(targetDir), maxDepth)) {
            s.forEach(path -> {
                FileOrDirMetaVO m = new FileOrDirMetaVO();
                m.convertFromFile(path.toFile());
                re.add(m);
            });
        } catch (IOException e) {
            String msg = "Error fetch files or directories from " + targetDir + ",msg:" + ExceptionUtils.getRootCauseMessage(e);
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }

        return re;
    }
}
