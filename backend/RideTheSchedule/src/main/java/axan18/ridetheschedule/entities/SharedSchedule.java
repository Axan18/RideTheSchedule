package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
public class SharedSchedule {
    public SharedSchedule(UUID id, Schedule schedule, AppUser scheduleOwner, AppUser sharedWith) {
        this.id = id;
        this.setSchedule(schedule);
        this.setScheduleOwner(scheduleOwner);
        this.setSharedWith(sharedWith);
    }
    @Id
    @UuidGenerator
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false )
    private UUID id;

    @ManyToOne
    private Schedule schedule;

    @ManyToOne
    private AppUser scheduleOwner;

    @ManyToOne
    private AppUser sharedWith;

    public void setSchedule(Schedule schedule) {
        if (this.schedule != null) {
            this.schedule.getSharedSchedules().remove(this);
        }
        this.schedule = schedule;
        if (schedule != null) {
            schedule.getSharedSchedules().add(this);
        }
    }

    public void setScheduleOwner(AppUser scheduleOwner) {
        if (this.scheduleOwner != null) {
            this.scheduleOwner.getSharedSchedulesOwned().remove(this);
        }
        this.scheduleOwner = scheduleOwner;
        if (scheduleOwner != null) {
            scheduleOwner.getSharedSchedulesOwned().add(this);
        }
    }
    public void setSharedWith(AppUser sharedWith) {
        if (this.sharedWith != null) {
            this.sharedWith.getSharedSchedulesWith().remove(this);
        }
        this.sharedWith = sharedWith;
        if (sharedWith != null) {
            sharedWith.getSharedSchedulesWith().add(this);
        }
    }

}
