package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
public class ScheduleTask {
        public ScheduleTask(UUID id, String name, String description, Timestamp createdAt, Timestamp lastModified, LocalDateTime startTime, LocalDateTime endTime, Schedule schedule, RecurringTask recurringTask, Set<Tag> tags, Set<ScheduleTaskComment> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
        this.setSchedule(schedule);
        this.recurringTask = recurringTask;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = tags;
        this.comments = comments;

    }
    @Id
    @UuidGenerator
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false )
    private UUID id;

    @NotNull
    @NotBlank
    @Column(length = 50)
    private String name;

    @NotBlank
    @Column(length = 255)
    private String description;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp lastModified;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private Schedule schedule;

    @OneToOne
    private RecurringTask recurringTask;

    @ManyToMany
    @JoinTable(
            name = "schedule_task_tags",
            joinColumns = @JoinColumn(name = "schedule_task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "scheduleTask", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
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
