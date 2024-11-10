package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
public class Schedule {

    public Schedule(UUID id, Date date, Date createdDate, Timestamp lastModified, AppUser appUser, Set<SharedSchedule> sharedSchedules) {
        this.id = id;
        this.date = date;
        this.createdDate = createdDate;
        this.lastModified = lastModified;
        this.setAppUser(appUser);
        this.sharedSchedules = sharedSchedules;
    }

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "CHAR(36)", updatable = false, nullable = false )
    private UUID id;

    @NotNull
    private Date date;

    @NotNull
    @Column(updatable = false)
    private Date createdDate;

    @UpdateTimestamp
    private Timestamp lastModified;

    @ManyToOne
    private AppUser appUser;

    @Builder.Default
    @OneToMany(mappedBy = "schedule")
    private Set<SharedSchedule> sharedSchedules = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "schedule")
    private Set<ScheduleTask> scheduleTasks = new HashSet<>();

    public void setAppUser(AppUser appUser) {
        if (this.appUser != null) {
            this.appUser.getSchedules().remove(this);
        }
        this.appUser = appUser;
        if (appUser != null) {
            appUser.getSchedules().add(this);
        }
    }
}
