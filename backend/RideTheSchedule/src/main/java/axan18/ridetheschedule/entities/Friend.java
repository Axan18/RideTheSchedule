package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Friend {
    @EmbeddedId
    private FriendId id;

    @NotNull
    private Status status;

    @UpdateTimestamp
    private Timestamp lastModified;

    @ManyToOne
    @MapsId("id1") //mapping id1 to appuser
    @JoinColumn(name = "id1", nullable = false)
    private AppUser user1;

    @ManyToOne
    @MapsId("id2")
    @JoinColumn(name = "id2", nullable = false)
    private AppUser user2;

    enum Status{
        PENDING,
        ACCEPTED
    }
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class FriendId implements Serializable {
        private UUID id1;
        private UUID id2;
    }
}
