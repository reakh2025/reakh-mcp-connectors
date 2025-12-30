package ai.reakh.mcp.connectors.local_fs.model.fo;

import ai.reakh.mcp.connectors.local_fs.mcp.McpLabel;
import ai.reakh.mcp.sdk.annotation.McpField;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrepFO {

    @NotBlank()
    @McpField(value = McpLabel.GREP_TARGET_FILE_OR_DIR)
    private String  targetFileOrDir;

    @McpField(value = McpLabel.GREP_IS_DIRECTORY)
    private boolean isDirectory;

    @NotBlank()
    @McpField(value = McpLabel.GREP_REGEX)
    private String  regex;

    @McpField(value = McpLabel.GREP_INCLUDE)
    private String  include;

    @McpField(value = McpLabel.GREP_EXCLUDE)
    private String  exclude;
}
