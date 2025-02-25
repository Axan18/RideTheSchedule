package axan18.ridetheschedule.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoTask {

    @Id
    @UuidGenerator
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false )
    private UUID id;
    @NotNull
    @NotBlank
    private String name;
    @NotBlank
    @Column(length = 100)
    private String description;
    private Boolean completed;
    private Boolean important;

    @Column(name = "scheduleId")
    private UUID scheduleId;

}
