package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.models.AppUserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AppUserMapper {
    AppUserDTO toAppUserDTO(AppUser appUser);
    AppUser toAppUser(AppUserDTO appUserDTO);
}
