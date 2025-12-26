package ai.reakh.mcp.connectors.local_fs.model.fo;

import javax.validation.constraints.NotBlank;

import ai.reakh.mcp.connectors.local_fs.mcp.McpLabel;
import ai.reakh.mcp.sdk.annotation.McpField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListCurrDirFilesAndSubDirsFO {

    @NotBlank()
    @McpField(value = McpLabel.DIR_PATH)
    private String  dirPath;

    @McpField(value = McpLabel.MAX_DEPTH)
    private Integer maxDepth;
}
