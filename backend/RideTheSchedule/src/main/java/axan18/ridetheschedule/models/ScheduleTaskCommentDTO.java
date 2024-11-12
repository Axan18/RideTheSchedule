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
public class ScheduleTaskCommentDTO {

    private UUID id;
    @NotNull
    @NotBlank
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
