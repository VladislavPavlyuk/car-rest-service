package ua.com.foxmined.carrestservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

// Entity representing a log entry stored in the database.
 
@Entity
@Getter
@Setter
@Table(name = "log_data")
public class LogData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Log severity level. 
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private LogLevel level;

    // Source identifier (e.g. class name, component). 
    @Column(name = "src", columnDefinition = "TEXT")
    private String src;

    // Log message text.
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogData logData = (LogData) o;
        return Objects.equals(id, logData.id)
                && Objects.equals(level, logData.level)
                && Objects.equals(src, logData.src)
                && Objects.equals(message, logData.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level, src, message);
    }

    @Override
    public String toString() {
        return "LogData{id=" + id + ", level=" + level + ", src='" + src + "', message='" + message + "'}";
    }
}