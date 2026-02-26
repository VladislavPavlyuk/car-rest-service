package ua.com.foxmined.carrestservice.dto;

import lombok.Getter;
import lombok.Setter;
import ua.com.foxmined.carrestservice.model.LogLevel;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LogDataDto {

    @NotNull(message = "level is required")
    private LogLevel level;

    private String src;

    private String message;
}
