package ua.com.foxmined.carrestservice.service.logdataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dao.logdata.LogDataRepository;
import ua.com.foxmined.carrestservice.model.LogData;
import ua.com.foxmined.carrestservice.model.LogLevel;

import java.util.Optional;

/**
 * Implementation of {@link LogDataService}.
 */
@Service
public class LogDataServiceImpl implements LogDataService {

    @Autowired
    private LogDataRepository logDataRepository;

    @Override
    public LogData save(LogData logData) {
        return logDataRepository.save(logData);
    }

    @Override
    public void update(LogData logData) {
        logDataRepository.save(logData);
    }

    @Override
    public void delete(LogData logData) {
        logDataRepository.delete(logData);
    }

    @Override
    public Page<LogData> findAll(Pageable pageable) {
        return logDataRepository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        logDataRepository.deleteAll();
    }

    @Override
    public Optional<LogData> findById(Long id) {
        return logDataRepository.findById(id);
    }

    @Override
    public Page<LogData> findByLevel(LogLevel level, Pageable pageable) {
        return logDataRepository.findByLevel(level, pageable);
    }
}
