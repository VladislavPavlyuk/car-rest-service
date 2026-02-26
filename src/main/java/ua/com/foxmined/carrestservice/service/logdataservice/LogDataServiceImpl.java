package ua.com.foxmined.carrestservice.service.logdataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dao.logdata.LogDataRepository;
import ua.com.foxmined.carrestservice.model.LogData;
import ua.com.foxmined.carrestservice.model.LogLevel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<LogData> findAllLimited(Integer limit) {
        if (limit == null) {
            return StreamSupport.stream(logDataRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList());
        }
        return logDataRepository.findAll(org.springframework.data.domain.PageRequest.of(0, limit)).toList();
    }
}
