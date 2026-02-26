package ua.com.foxmined.carrestservice.dto;

import lombok.Getter;
import lombok.Setter;
import ua.com.foxmined.carrestservice.model.LogLevel;

import javax.validation.constraints.NotNull;

/**
 * DTO for creating or updating a log entry.
 */
@Getter
@Setter
public class LogDataDto {

    /** Log severity level. */
    @NotNull(message = "level is required")
    private LogLevel level;

    /** Source identifier (e.g. class name, component). */
    private String src;

    /** Log message text. */
    private String message;
}
