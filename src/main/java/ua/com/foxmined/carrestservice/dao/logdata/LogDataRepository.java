package ua.com.foxmined.carrestservice.dao.logdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.model.LogData;
import ua.com.foxmined.carrestservice.model.LogLevel;

@Repository
@Transactional
public interface LogDataRepository extends PagingAndSortingRepository<LogData, Long> {

    Page<LogData> findAll(Pageable pageable);

    Page<LogData> findByLevel(LogLevel level, Pageable pageable);
}
