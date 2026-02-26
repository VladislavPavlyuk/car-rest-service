package ua.com.foxmined.carrestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response DTO containing only log entry id.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogIdResponse {

    private Long id;
}
