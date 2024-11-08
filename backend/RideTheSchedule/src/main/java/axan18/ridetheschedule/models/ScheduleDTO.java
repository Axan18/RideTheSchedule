package axan18.ridetheschedule.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private UUID id;
    private Date date;
    private Date createdDate;
    private Timestamp lastModified;

}
