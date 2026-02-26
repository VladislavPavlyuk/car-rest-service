package ua.com.foxmined.carrestservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.foxmined.carrestservice.dto.LogDataDto;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.model.LogData;
import ua.com.foxmined.carrestservice.model.LogLevel;
import ua.com.foxmined.carrestservice.service.logdataservice.LogDataService;
import ua.com.foxmined.carrestservice.utils.EndPoints;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = EndPoints.VERSION_1)
@SecurityRequirement(name = "bearerAuth")
public class LogDataController {

    @Autowired
    private LogDataService logDataService;

    @RequestMapping(value = EndPoints.GET_LOGS, method = RequestMethod.GET)
    public ResponseEntity<List<LogData>> getLogs(
            @RequestParam(name = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) @Max(100) Integer pageSize,
            @RequestParam(name = "level", required = false) LogLevel level) {
        List<LogData> logs = level != null
                ? logDataService.findByLevel(level, PageRequest.of(page, pageSize)).toList()
                : logDataService.findAll(PageRequest.of(page, pageSize)).toList();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.GET_LOG_BY_ID, method = RequestMethod.GET)
    public ResponseEntity<LogData> getLogById(@PathVariable Long id) {
        return logDataService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = EndPoints.SET_LOG, method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create:items')")
    public ResponseEntity<LogData> createLog(@Valid @RequestBody LogDataDto dto) {
        LogData logData = new LogData();
        logData.setLevel(dto.getLevel());
        logData.setSrc(dto.getSrc());
        logData.setMessage(dto.getMessage());
        return new ResponseEntity<>(logDataService.save(logData), HttpStatus.CREATED);
    }

    @RequestMapping(value = EndPoints.UPDATE_LOG, method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('update:items')")
    public ResponseEntity<?> updateLog(@PathVariable Long id, @Valid @RequestBody LogDataDto dto) {
        LogData logData = logDataService.findById(id)
                .orElseThrow(() -> new EntityNotPresentException("LogData with id " + id + " not found"));
        logData.setLevel(dto.getLevel());
        logData.setSrc(dto.getSrc());
        logData.setMessage(dto.getMessage());
        logDataService.update(logData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.DELETE_LOG, method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('delete:items')")
    public ResponseEntity<?> deleteLog(@PathVariable Long id) {
        LogData logData = logDataService.findById(id)
                .orElseThrow(() -> new EntityNotPresentException("LogData with id " + id + " not found"));
        logDataService.delete(logData);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
