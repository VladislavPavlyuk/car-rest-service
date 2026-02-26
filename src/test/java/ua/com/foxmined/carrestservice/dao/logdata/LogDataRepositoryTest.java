package ua.com.foxmined.carrestservice.dao.logdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.com.foxmined.carrestservice.model.LogData;
import ua.com.foxmined.carrestservice.model.LogLevel;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("repository-test")
class LogDataRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.url", postgres::getJdbcUrl);
        registry.add("spring.flyway.user", postgres::getUsername);
        registry.add("spring.flyway.password", postgres::getPassword);
    }

    @Autowired
    private LogDataRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void save_WhenValidLogData_ReturnsSavedEntityWithId() {
        LogData logData = new LogData();
        logData.setLevel(LogLevel.INFO);
        logData.setSrc("test-service");
        logData.setMessage("Test message");

        LogData actualResult = repository.save(logData);
        var expectedLevel = LogLevel.INFO;
        var expectedSrc = "test-service";
        var expectedMessage = "Test message";

        assertThat(actualResult).matches(log ->
                log.getId() != null
                        && log.getLevel() == expectedLevel
                        && expectedSrc.equals(log.getSrc())
                        && expectedMessage.equals(log.getMessage()));
    }

    @Test
    void findAll_WhenDataExists_ReturnsPaginatedResults() {
        LogData log1 = createAndSave(LogLevel.DEBUG, "src1", "msg1");
        LogData log2 = createAndSave(LogLevel.ERROR, "src2", "msg2");

        List<LogData> actualResult = repository.findAll(PageRequest.of(0, 10)).toList();
        var expectedIds = List.of(log1.getId(), log2.getId());

        assertThat(actualResult).extracting(LogData::getId).containsExactlyInAnyOrderElementsOf(expectedIds);
    }

    @Test
    void findByLevel_WhenLevelFilterProvided_ReturnsOnlyMatchingEntries() {
        createAndSave(LogLevel.INFO, "src1", "msg1");
        createAndSave(LogLevel.DEBUG, "src2", "msg2");
        createAndSave(LogLevel.INFO, "src3", "msg3");

        List<LogData> actualResult = repository.findByLevel(LogLevel.INFO, PageRequest.of(0, 10)).toList();
        var expectedSize = 2;
        var expectedLevel = LogLevel.INFO;

        assertThat(actualResult).hasSize(expectedSize).allMatch(log -> log.getLevel() == expectedLevel);
    }

    @Test
    void findById_WhenEntityExists_ReturnsOptionalWithEntity() {
        LogData saved = createAndSave(LogLevel.INFO, "src", "msg");

        var actualResult = repository.findById(saved.getId());
        var expectedMessage = "msg";

        assertThat(actualResult).isPresent().get().extracting(LogData::getMessage).isEqualTo(expectedMessage);
    }

    @Test
    void findById_WhenEntityNotExists_ReturnsEmptyOptional() {
        var actualResult = repository.findById(999L);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void countViaJdbcTemplate_WhenDataExists_ReturnsCorrectCount() {
        createAndSave(LogLevel.INFO, "src1", "msg1");
        createAndSave(LogLevel.DEBUG, "src2", "msg2");

        Integer actualResult = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM log_data", Integer.class);
        var expectedResult = 2;

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private LogData createAndSave(LogLevel level, String src, String message) {
        LogData log = new LogData();
        log.setLevel(level);
        log.setSrc(src);
        log.setMessage(message);
        return repository.save(log);
    }
}
