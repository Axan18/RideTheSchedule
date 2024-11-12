package axan18.ridetheschedule.models;

import axan18.ridetheschedule.entities.RecurringTask;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecurringTaskDTO {
    @NotNull
    private UUID id;
    @NotNull
    private RecurringTask.Frequency frequency;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
}
