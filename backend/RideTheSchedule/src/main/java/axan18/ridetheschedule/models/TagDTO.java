package axan18.ridetheschedule.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TagDTO {
    private UUID id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private String color;
}
