package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
public class Tag {
    public Tag(UUID id, String name, String color, Set<ScheduleTask> scheduleTasks) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.scheduleTasks = scheduleTasks;
    }
    @Id
    @UuidGenerator
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false )
    private UUID id;

    @Column(length = 50)
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private String color;

    @Builder.Default
    @ManyToMany(mappedBy = "tags")
    private Set<ScheduleTask> scheduleTasks = new HashSet<>();
}
