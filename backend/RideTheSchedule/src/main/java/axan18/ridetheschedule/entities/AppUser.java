package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "app_user", schema = "myschema")
public class AppUser {
    @Id
    @UuidGenerator
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false )
    private UUID id;

    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String username;

//    @NotNull
//    @NotBlank
    @Size(max = 60)
    @Column(length = 60, columnDefinition = "varchar(60)", nullable = false)
    private String password;

    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(columnDefinition = "varchar(255)", nullable = false, unique = true)
    @Email
    private String email;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp lastModified;

    @NotNull
    private Date lastLoginDate;

    @NotNull
    private Boolean isActive;

    @Builder.Default
    @OneToMany(mappedBy = "appUser", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Set<Schedule> schedules = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "scheduleOwner")
    private Set<SharedSchedule> sharedSchedulesOwned = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "sharedWith")
    private Set<SharedSchedule> sharedSchedulesWith = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "author")
    private Set<ScheduleTaskComment> comments = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user1")
    private Set<Friendship> friendsSent = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user2")
    private Set<Friendship> friendsReceived = new HashSet<>();

}
