package ai.reakh.mcp.connectors.local_fs.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
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

    @Override
    public void createFile(String targetFile) {
        Path filePath = Paths.get(targetFile);

        if (Files.exists(filePath)) {
            throw new IllegalArgumentException("File " + targetFile + " is exist, can not create it.");
        }

        checkParentDirOrCreate(filePath);

        try {
            Files.createFile(filePath);

            log.info("File {} success created.", targetFile);
        } catch (Exception e) {
            String msg = "Create file " + targetFile + " failed,msg:" + ExceptionUtils.getRootCauseMessage(e);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public void createDirectory(String targetDir) {
        Path filePath = Paths.get(targetDir);

        if (Files.exists(filePath)) {
            return;
        }

        checkParentDirOrCreate(filePath);

        try {
            Files.createDirectory(filePath);

            log.info("Directory {} success created.", filePath);
        } catch (Exception e) {
            String msg = "Create directory " + filePath + " failed,msg:" + ExceptionUtils.getRootCauseMessage(e);
            throw new RuntimeException(msg, e);
        }
    }

    protected void checkParentDirOrCreate(Path filePath) {
        Path parentDir = filePath.getParent();

        if (parentDir == null) {
            throw new IllegalArgumentException("Parent directory is not exist.");
        }

        try {
            if (!Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
        } catch (Exception e) {
            String msg = "Create parent directory failed,msg:" + ExceptionUtils.getRootCauseMessage(e);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public void writeFile(String targetFile, String content, boolean append) {
        Path filePath = Paths.get(targetFile);

        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("File " + targetFile + " is not exist, can not write it.");
        }

        if (!Files.isRegularFile(filePath)) {
            throw new IllegalArgumentException("File is not a regular file: " + targetFile);
        }

        if (!Files.isWritable(filePath)) {
            throw new IllegalArgumentException("File is not writable,please check the privileges: " + targetFile);
        }

        try {
            byte[] bContent = (content != null ? content : "").getBytes(StandardCharsets.UTF_8);
            if (append) {
                Files.write(filePath, bContent, StandardOpenOption.APPEND);
                log.info("Success append to file: {}, content length: {}", targetFile, (content != null ? content.length() : 0));
            } else {
                Files.write(filePath, bContent, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                log.info("Success to overwrite: {}, content length: {}", targetFile, (content != null ? content.length() : 0));
            }
        } catch (AccessDeniedException e) {
            String msg = "Access denied, maybe have no rights to writeWrite content to file " + targetFile + " failed,msg:" + ExceptionUtils.getRootCauseMessage(e);
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        } catch (Exception e) {
            String msg = "Write content to file " + targetFile + " failed,msg:" + ExceptionUtils.getRootCauseMessage(e);
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }
}
