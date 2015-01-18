package ee.esutoniagodesu.pojo.dto;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

public class LoggerDTO implements Serializable {

    private static final long serialVersionUID = 6088212600454873007L;
    private String name;
    private String level;

    public LoggerDTO(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    @JsonCreator
    public LoggerDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "LoggerDTO{" +
            "name='" + name + '\'' +
            ", level='" + level + '\'' +
            '}';
    }
}
