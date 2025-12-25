package ai.reakh.mcp.connectors.local_fs.model.fo;

import ai.reakh.mcp.connectors.local_fs.mcp.McpLabel;
import ai.reakh.mcp.sdk.annotation.McpField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadFileToTextFO {

    @McpField(value = McpLabel.FILE_PATH)
    private String filePath;
}
