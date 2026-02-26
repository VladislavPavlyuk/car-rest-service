package ua.com.foxmined.carrestservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.foxmined.carrestservice.dto.LogDataDto;
import ua.com.foxmined.carrestservice.dto.LogIdResponse;
import ua.com.foxmined.carrestservice.model.LogData;
import ua.com.foxmined.carrestservice.service.logdataservice.LogDataService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * REST controller for log data API.
 * <p>
 * POST /api/logs - save new log entry
 * GET /api/logs?limit=N - get list (limit optional)
 */
@RestController
@RequestMapping("/api/logs")
public class LogDataController {

    private final LogDataService logDataService;

    public LogDataController(LogDataService logDataService) {
        this.logDataService = logDataService;
    }

    /**
     * Saves a new log entry.
     *
     * @param dto log data (level, src, message)
     * @return response with created id
     */
    @PostMapping
    @PreAuthorize("hasAuthority('create:items')")
    public ResponseEntity<LogIdResponse> createLog(@Valid @RequestBody LogDataDto dto) {
        LogData saved = logDataService.createFromDto(dto);
        return new ResponseEntity<>(new LogIdResponse(saved.getId()), HttpStatus.CREATED);
    }

    /**
     * Returns list of log entries.
     *
     * @param limit optional max number of records (omit for all)
     * @return list of log entries
     */
    @GetMapping
    public ResponseEntity<List<LogData>> getLogs(
            @RequestParam(name = "limit", required = false) @Min(1) @Max(1000) Integer limit) {
        List<LogData> logs = logDataService.findAllLimited(limit);
        return ResponseEntity.ok(logs);
    }
}
