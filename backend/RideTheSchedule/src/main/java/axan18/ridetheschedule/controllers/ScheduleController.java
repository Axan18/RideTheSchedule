package axan18.ridetheschedule.controllers;

import axan18.ridetheschedule.models.ScheduleTaskDTO;
import axan18.ridetheschedule.services.JwtService;
import axan18.ridetheschedule.services.ScheduleTaskService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ScheduleController {
    private final ScheduleTaskService scheduleTaskService;
    private final JwtService jwtService;
    public static final String SCHEDULE_PATH = "/schedules";

    @PostMapping(SCHEDULE_PATH+"/add_task")
    public ResponseEntity addScheduleTask(@CookieValue(name = "JWT") String token, @Validated @RequestBody ScheduleTaskDTO scheduleTaskDTO){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        return scheduleTaskService.createScheduleTask(userID,scheduleTaskDTO)
                .map(taskDTO -> ResponseEntity.status(HttpStatus.CREATED).body(taskDTO))
                .orElse(ResponseEntity.of(ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)).build());
    }
}
