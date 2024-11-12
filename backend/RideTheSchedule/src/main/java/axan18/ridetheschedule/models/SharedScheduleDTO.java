package axan18.ridetheschedule.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SharedScheduleDTO {

    private UUID id;
    private UUID scheduleId;
    private UUID ownerId;
    private UUID sharedWithId;
}
