package ai.reakh.mcp.connectors.local_fs.model.vo;

import java.io.File;
import java.nio.file.FileSystems;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OsInfo {

    private String  osName;

    private String  osArch;

    private String  osVersion;

    private String  fileSeparator;

    private String  pathSeparator;

    private String  lineSeparator;

    private Integer listFileOrDirsMaxDepth;

    public void convertFromSysProp() {
        this.osName = System.getProperty("os.name");
        this.osArch = System.getProperty("os.arch");
        this.osVersion = System.getProperty("os.version");
        this.fileSeparator = FileSystems.getDefault().getSeparator();
        this.pathSeparator = File.pathSeparator;
        this.lineSeparator = System.lineSeparator();
        this.listFileOrDirsMaxDepth = 3;
    }
}
