package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
public class Tags {
    public Tags(UUID id, String name, String color, Set<Schedule> schedules) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.schedules = schedules;
    }
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "CHAR(36)", updatable = false, nullable = false )
    private UUID id;

    @Column(length = 50)
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private String color;

    @Builder.Default
    @ManyToMany(mappedBy = "tags")
    private Set<Schedule> schedules = new HashSet<>();
}
