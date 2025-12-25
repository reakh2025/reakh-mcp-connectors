package ai.reakh.mcp.connectors.local_fs.model.vo;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileOrDirMetaVO {

    private String  name;

    private String  fullPath;

    private boolean isDirectory;

    private boolean isFile;

    private boolean canRead;

    private boolean canWrite;

    private boolean canExecute;

    private long    length;

    public void convertFromFile(File f) {
        this.name = f.getName();
        this.fullPath = f.getPath();
        this.isDirectory = f.isDirectory();
        this.isFile = f.isFile();
        this.canExecute = f.canExecute();
        this.canRead = f.canRead();
        this.canWrite = f.canWrite();
        this.length = f.length();
    }
}
