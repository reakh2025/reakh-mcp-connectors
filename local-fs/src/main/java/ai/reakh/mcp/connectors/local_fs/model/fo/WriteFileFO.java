package ai.reakh.mcp.connectors.local_fs.model.fo;

import ai.reakh.mcp.connectors.local_fs.mcp.McpLabel;
import ai.reakh.mcp.sdk.annotation.McpField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class WriteFileFO {

    @NotBlank
    @McpField(value = McpLabel.WRITE_FILE_TARGET_FILE)
    private String  targetFile;

    @NotBlank
    @McpField(value = McpLabel.WRITE_FILE_CONTENT)
    private String  content;

    @McpField(value = McpLabel.WRITE_FILE_APPEND)
    private boolean append;
}
