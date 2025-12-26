package ai.reakh.mcp.connectors.local_fs.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
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

    @Override
    public List<String> grep(String targetFileOrDir, boolean isDirectory, String regex, String include, String exclude) {
        File f = new File(targetFileOrDir);
        if (!f.exists()) {
            throw new IllegalArgumentException("File or directory " + targetFileOrDir + " is not exist, use full path instead or check the existence of target.");
        }

        ProcessBuilder pb = new ProcessBuilder();
        if (isDirectory) {
            pb.command("grep", "-r", regex);
            if (StringUtils.isNotBlank(include)) {
                pb.command().add("--include=" + include);
            }

            if (StringUtils.isNotBlank(exclude)) {
                pb.command().add("--exclude=" + exclude);
            }

            pb.command().add(targetFileOrDir);
        } else {
            pb.command("grep", "-H", regex, targetFileOrDir);
        }

        String commandLine = String.join(" ", pb.command());
        log.info("Start to execute:" + commandLine);

        try {
            Process p = pb.start();

            List<String> re = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    re.add(line);
                }
            }

            return re;
        } catch (Exception e) {
            String msg = "Grep failed.msg:" + ExceptionUtils.getRootCauseMessage(e);
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }
}
