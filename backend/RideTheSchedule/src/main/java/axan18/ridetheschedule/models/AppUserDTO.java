package axan18.ridetheschedule.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    @NotNull
    private UUID id;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    @NotNull
    private Date lastLoginDate;
    @NotNull
    private Boolean isActive;
}
