package axan18.ridetheschedule.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

import java.sql.Date;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private UUID id;
    @NotNull
    @NotBlank
    private String username;
    private String password;
    private String email;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
    private Date lastLoginDate;
    private Boolean isActive;
}
