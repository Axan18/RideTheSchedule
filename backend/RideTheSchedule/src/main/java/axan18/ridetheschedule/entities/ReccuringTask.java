package axan18.ridetheschedule.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
public class ReccuringTask {

    public ReccuringTask(UUID id, Frequency frequency, ScheduleTask scheduleTask, Date startDate, Date endDate) {
        this.id = id;
        this.frequency = frequency;
        this.scheduleTask = scheduleTask;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
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
