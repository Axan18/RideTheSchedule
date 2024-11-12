package axan18.ridetheschedule.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTaskDTO {
    private UUID id;
    @NotNull
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;

}
