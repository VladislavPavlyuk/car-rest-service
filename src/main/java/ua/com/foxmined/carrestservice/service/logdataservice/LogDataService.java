package ua.com.foxmined.carrestservice.service.logdataservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxmined.carrestservice.model.LogData;
import ua.com.foxmined.carrestservice.model.LogLevel;
import ua.com.foxmined.carrestservice.service.DAOInterface;

import java.util.Optional;

/**
 * Service for log data business logic.
 */
public interface LogDataService extends DAOInterface<LogData> {

    /**
     * Finds a log entry by id.
     *
     * @param id log entry id
     * @return optional log entry
     */
    Optional<LogData> findById(Long id);

    /**
     * Finds log entries by severity level with pagination.
     *
     * @param level    log level filter
     * @param pageable pagination parameters
     * @return page of log entries
     */
    Page<LogData> findByLevel(LogLevel level, Pageable pageable);
}
