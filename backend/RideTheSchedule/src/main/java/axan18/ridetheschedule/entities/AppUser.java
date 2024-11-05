package axan18.ridetheschedule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import jakarta.validation.constraints.NotNull;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class AppUser {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "CHAR(36)", updatable = false, nullable = false )
    private UUID id;

    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String username;

    @NotNull
    @NotBlank
    @Size(max = 60)
    @Column(length = 60, columnDefinition = "varchar(60)", nullable = false)
    private String password;

    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String email;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @NotNull
    private Date lastLoginDate;

    @NotNull
    private Boolean isActive;
}
