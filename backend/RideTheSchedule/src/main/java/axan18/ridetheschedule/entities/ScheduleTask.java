package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
public class ScheduleTask {
    public ScheduleTask(UUID id, String name, String description, Timestamp createdAt, Timestamp lastModified, Schedule schedule) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
        this.setSchedule(schedule);
    }
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "CHAR(36)", updatable = false, nullable = false )
    private UUID id;

    @NotNull
    @NotBlank
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(length = 255)
    private String description;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp lastModified;

    @ManyToOne
    private Schedule schedule;

    @OneToOne
    private ReccuringTask reccuringTask;

    @ManyToMany
    @JoinTable(
            name = "schedule_task_tags",
            joinColumns = @JoinColumn(name = "schedule_task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tags> tags = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "scheduleTask")
    private Set<ScheduleTaskComment> comments = new HashSet<>();

    public void setSchedule(Schedule schedule) {
        if (this.schedule != null) {
            this.schedule.getScheduleTasks().remove(this);
        }
        this.schedule = schedule;
        if (schedule != null) {
            schedule.getScheduleTasks().add(this);
        }
    }

}
