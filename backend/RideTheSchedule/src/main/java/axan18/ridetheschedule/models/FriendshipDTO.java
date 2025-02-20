package axan18.ridetheschedule.models;

import axan18.ridetheschedule.entities.Friendship;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDTO {
    @NotNull
    private UUID user1Id;

    @NotNull
    private UUID user2Id;

    @NotNull
    private Friendship.Status status;

    @NotNull
    private Timestamp lastModified;

}
