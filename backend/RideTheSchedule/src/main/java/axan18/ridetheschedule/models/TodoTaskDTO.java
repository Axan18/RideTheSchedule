package axan18.ridetheschedule.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoTaskDTO {
    private UUID id;
    @NotNull
    @NotBlank
    private String name;
    @NotBlank
    @Column(length = 100)
    private String description;
    private Boolean completed;
    private Boolean important;

}
