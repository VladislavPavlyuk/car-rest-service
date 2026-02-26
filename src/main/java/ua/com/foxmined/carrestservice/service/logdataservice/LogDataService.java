package ua.com.foxmined.carrestservice.service.logdataservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxmined.carrestservice.model.LogData;
import ua.com.foxmined.carrestservice.model.LogLevel;
import ua.com.foxmined.carrestservice.service.DAOInterface;

import java.util.Optional;

public interface LogDataService extends DAOInterface<LogData> {

    Optional<LogData> findById(Long id);

    Page<LogData> findByLevel(LogLevel level, Pageable pageable);
}
