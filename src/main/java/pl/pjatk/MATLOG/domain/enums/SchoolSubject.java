package pl.pjatk.MATLOG.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum that represents school subjects
 */
public enum SchoolSubject {
    MATHEMATICS, LOGIC;

    @JsonValue
    public String toValue() {
        return name();
    }

    @JsonCreator
    public static SchoolSubject fromValue(String value) {
        for (SchoolSubject schoolSubject : values()) {
            if (schoolSubject.name().equalsIgnoreCase(value)) {
                return schoolSubject;
            }
        }
        throw new IllegalArgumentException("Invalid SchoolSubject: " + value);
    }
}
