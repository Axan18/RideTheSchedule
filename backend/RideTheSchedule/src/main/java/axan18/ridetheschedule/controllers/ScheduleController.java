package axan18.ridetheschedule.controllers;

import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.models.ScheduleDTO;
import axan18.ridetheschedule.models.ScheduleTaskDTO;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import axan18.ridetheschedule.services.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ScheduleController {
    private final ScheduleTaskService scheduleTaskService;
    private final ScheduleService scheduleService;
    private final SharedScheduleService sharedScheduleService;
    private final JwtService jwtService;

    public static final String SCHEDULE_PATH = "/schedules";
    public static final String SCHEDULE_PATH_DAY = SCHEDULE_PATH+"/{dateString}";
    public static final String SHARED_SCHEDULE_PATH = SCHEDULE_PATH+"/shared";
    private final AppUserRepository appUserRepository;

    @PostMapping(SCHEDULE_PATH)
    public ResponseEntity addScheduleTask(@CookieValue(name = "JWT") String token, @Validated @RequestBody ScheduleTaskDTO scheduleTaskDTO){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        return scheduleTaskService.createScheduleTask(userID,scheduleTaskDTO)
                .map(taskDTO -> ResponseEntity.status(HttpStatus.CREATED).body(taskDTO))
                .orElse(ResponseEntity.of(ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)).build());
    }
    @GetMapping(value = SCHEDULE_PATH_DAY)
    public ResponseEntity getScheduleTasks(@CookieValue(name = "JWT") String token, @PathVariable("dateString") String dateString){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        java.sql.Date date = Date.valueOf(dateString);
        Optional<ScheduleDTO> opt = scheduleService.getScheduleForDate(userID,date);
        if(opt.isEmpty()){ //no task has been added yet
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<ScheduleTaskDTO> tasks = scheduleTaskService.getTasksForSchedule(opt.get().getId());
        return ResponseEntity.ok(tasks);
    }
    @PostMapping(SHARED_SCHEDULE_PATH+"/{dateString}")
    public ResponseEntity addSharedSchedule(@CookieValue(name = "JWT") String token, @PathVariable("dateString") String dateString, @RequestParam String sharedWithId){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        SharedScheduleDTO ssDTO = SharedScheduleDTO.builder()// get handling necessary
                .scheduleId(scheduleService.getScheduleForDate(userID,Date.valueOf(dateString)).get().getId())
                .sharedWithId(UUID.fromString(sharedWithId))
                .ownerId(userID)
                .build();
        return ResponseEntity.ok().body(sharedScheduleService.createSharedSchedule(ssDTO));
    }
    @GetMapping(value = SHARED_SCHEDULE_PATH+ "/{dateString}")
    public ResponseEntity getScheduleTasks(@CookieValue(name = "JWT") String token, @PathVariable("dateString") String dateString, @RequestParam String sharedWithId){
        Claims claims = jwtService.parseToken(token);
        UUID user1ID = UUID.fromString(claims.getSubject());
        UUID user2ID = UUID.fromString(sharedWithId);
        String user2name = appUserRepository.getUsernameByID(user2ID);
        java.sql.Date date = Date.valueOf(dateString);
        Optional<ScheduleDTO> schedules1 = scheduleService.getScheduleForDate(user1ID,date);
        Optional<ScheduleDTO> schedules2 = scheduleService.getScheduleForDate(user2ID,date);
        if(schedules1.isEmpty() && schedules2.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<ScheduleTaskDTO> tasks1 = scheduleTaskService.getTasksForSchedule(schedules1.get().getId());
        List<ScheduleTaskDTO> tasks2 = scheduleTaskService.getTasksForSchedule(schedules2.get().getId());
        tasks2.forEach(task ->{
            task.setName(user2name+"-"+task.getName());
        });
        tasks1.addAll(tasks2);
        return ResponseEntity.ok(tasks1);
    }
}
