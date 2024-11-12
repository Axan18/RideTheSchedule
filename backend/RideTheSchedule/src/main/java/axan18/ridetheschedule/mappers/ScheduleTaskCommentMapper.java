package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.ScheduleTaskComment;
import axan18.ridetheschedule.models.ScheduleTaskCommentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ScheduleTaskCommentMapper {
    ScheduleTaskComment toScheduleTaskComment(ScheduleTaskCommentDTO scheduleTaskCommentDTO);
    ScheduleTaskCommentDTO toScheduleTaskCommentDTO(ScheduleTaskComment scheduleTaskComment);
}
