package ai.reakh.mcp.connectors.local_fs.mcp;

public interface McpLabel {

    String GET_OS_INFO                      = "GET_OS_INFO";

    String LIST_CURR_DIR_FILES_AND_SUB_DIRS = "LIST_CURR_DIR_FILES_AND_SUB_DIRS";

    String READ_FILE_TO_TEXT                = "READ_FILE_TO_TEXT";

    String DIR_PATH                         = "DIR_PATH";

    String MAX_DEPTH                        = "MAX_DEPTH";

    String FILE_PATH                        = "FILE_PATH";

    String GREP                             = "GREP";

    String GREP_TARGET_FILE_OR_DIR          = "GREP_TARGET_FILE_OR_DIR";

    String GREP_IS_DIRECTORY                = "GREP_IS_DIRECTORY";

    String GREP_REGEX                       = "GREP_REGEX";

    String GREP_INCLUDE                     = "GREP_INCLUDE";

    String GREP_EXCLUDE                     = "GREP_EXCLUDE";
}
