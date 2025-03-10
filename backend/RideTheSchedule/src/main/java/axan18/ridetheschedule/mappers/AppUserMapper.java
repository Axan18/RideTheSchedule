package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.models.AppUserPublicDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AppUserMapper {
    AppUserDTO toAppUserDTO(AppUser appUser);
    AppUser toAppUser(AppUserDTO appUserDTO);
    AppUser toAppUser(AppUserPublicDTO appUser);
    AppUserPublicDTO toAppUserPublicDTO(AppUser appUser);
    List<AppUserPublicDTO> toAppUserPublicDTOList(List<AppUser> list);
}
