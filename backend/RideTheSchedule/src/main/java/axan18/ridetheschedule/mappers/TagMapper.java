package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.Tag;
import axan18.ridetheschedule.models.TagDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TagMapper {
    Tag toTag(TagDTO tagDTO);
    TagDTO toTagDTO(Tag tag);
}
