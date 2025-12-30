package ai.reakh.mcp.connectors.local_fs.model.fo;


import ai.reakh.mcp.connectors.local_fs.mcp.McpLabel;
import ai.reakh.mcp.sdk.annotation.McpField;
import jakarta.validation.constraints.NotBlank;
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
