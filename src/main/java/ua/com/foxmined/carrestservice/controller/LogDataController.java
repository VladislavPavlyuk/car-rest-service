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

// POST /api/logs - create, GET /api/logs?limit=N - list
@RestController
@RequestMapping("/api/logs")
public class LogDataController {

    private final LogDataService logDataService;

    public LogDataController(LogDataService logDataService) {
        this.logDataService = logDataService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create:items')")
    public ResponseEntity<LogIdResponse> createLog(@Valid @RequestBody LogDataDto dto) {
        LogData saved = logDataService.createFromDto(dto);
        return new ResponseEntity<>(new LogIdResponse(saved.getId()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LogData>> getLogs(
            @RequestParam(name = "limit", required = false) @Min(1) @Max(1000) Integer limit) {
        List<LogData> logs = logDataService.findAllLimited(limit);
        return ResponseEntity.ok(logs);
    }
}
