package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
public class RecurringTask {

    public RecurringTask(UUID id, Frequency frequency, ScheduleTask scheduleTask, Date startDate, Date endDate) {
        this.id = id;
        this.frequency = frequency;
        this.scheduleTask = scheduleTask;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @Id
    @UuidGenerator
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false )
    private UUID id;

    @NotNull
    private Frequency frequency;

    @OneToOne
    private ScheduleTask scheduleTask;

    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;

    public enum Frequency {
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }
}
