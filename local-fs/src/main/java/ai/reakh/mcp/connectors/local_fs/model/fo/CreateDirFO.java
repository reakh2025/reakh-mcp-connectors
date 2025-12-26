package ai.reakh.mcp.connectors.local_fs.model.fo;

import javax.validation.constraints.NotBlank;

import ai.reakh.mcp.connectors.local_fs.mcp.McpLabel;
import ai.reakh.mcp.sdk.annotation.McpField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDirFO {

    @NotBlank
    @McpField(value = McpLabel.CREATE_FILE_TARGET_DIR)
    private String targetDir;
}
