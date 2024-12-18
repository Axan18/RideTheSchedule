package axan18.ridetheschedule.mappers;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class DateMapper {
    @Named("LocalDateTimeToTimestamp")
    public java.sql.Timestamp LocalDateTimeToTimestamp(java.time.LocalDateTime localDateTime) {
        return java.sql.Timestamp.valueOf(localDateTime);
    }
    @Named("TimestampToLocalDateTime")
    public java.time.LocalDateTime TimestampToLocalDateTime(java.sql.Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
