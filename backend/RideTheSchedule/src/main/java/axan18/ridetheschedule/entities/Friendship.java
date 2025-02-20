package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Friendship {
    @EmbeddedId
    private FriendshipId id;

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

    public enum Status{
        PENDING,
        ACCEPTED
    }
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class FriendshipId implements Serializable {//key from two ids
        private UUID id1; //sender
        private UUID id2; //receiver
    }
}
