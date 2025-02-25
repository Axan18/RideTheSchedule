package axan18.ridetheschedule.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ScheduleTaskComment {

    public ScheduleTaskComment(UUID id, String content, Timestamp createdAt, Timestamp lastModified, ScheduleTask scheduleTask, AppUser appUser) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
        this.setScheduleTask(scheduleTask);
        this.setAppUser(appUser);
    }
    @Id
    @UuidGenerator
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false )
    private UUID id;

    @NotNull
    @NotBlank
    @Column(length = 255)
    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp lastModified;

    @ManyToOne
    private ScheduleTask scheduleTask;

    @ManyToOne
    private AppUser author;

    public void setScheduleTask(ScheduleTask scheduleTask) {
        if (this.scheduleTask != null) {
            this.scheduleTask.getComments().remove(this);
        }
        this.scheduleTask = scheduleTask;
        if (scheduleTask != null) {
            scheduleTask.getComments().add(this);
        }
    }
    public void setAppUser(AppUser appUser) {
        if (this.author != null) {
            this.author.getComments().remove(this);
        }
        this.author = appUser;
        if (appUser != null) {
            appUser.getComments().add(this);
        }
    }
}
