package ai.reakh.mcp.connectors.local_fs.model.fo;

import ai.reakh.mcp.connectors.local_fs.mcp.McpLabel;
import ai.reakh.mcp.sdk.annotation.McpField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateFileFO {

    @NotBlank
    @McpField(value = McpLabel.CREATE_FILE_TARGET_FILE)
    private String targetFile;
}
