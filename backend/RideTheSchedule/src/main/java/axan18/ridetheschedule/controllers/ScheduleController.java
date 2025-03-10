package axan18.ridetheschedule.controllers;

import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.models.ScheduleDTO;
import axan18.ridetheschedule.models.ScheduleTaskDTO;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import axan18.ridetheschedule.services.JwtService;
import axan18.ridetheschedule.services.ScheduleService;
import axan18.ridetheschedule.services.ScheduleTaskService;
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
    private final JwtService jwtService;
    public static final String SCHEDULE_PATH = "/schedules";
    public static final String SCHEDULE_PATH_DAY = SCHEDULE_PATH+"/{dateString}";
    public static final String SHARED_SCHEDULE_PATH = SCHEDULE_PATH+"/shared";
    
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
    @PostMapping(SHARED_SCHEDULE_PATH)
    public ResponseEntity addSharedSchedule(@CookieValue(name = "JWT") String token,){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        SharedScheduleDTO.builder()
                .scheduleId()
    }
}
