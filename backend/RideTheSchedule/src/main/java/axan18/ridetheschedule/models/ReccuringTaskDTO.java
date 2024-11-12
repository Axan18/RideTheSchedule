package axan18.ridetheschedule.models;

import axan18.ridetheschedule.entities.ReccuringTask;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReccuringTaskDTO {
    @NotNull
    private UUID id;
    @NotNull
    private ReccuringTask.Frequency frequency;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
}
