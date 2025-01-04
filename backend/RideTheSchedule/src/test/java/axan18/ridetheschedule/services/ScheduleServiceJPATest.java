package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.mappers.ScheduleMapper;
import axan18.ridetheschedule.models.ScheduleDTO;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceJPATest {
    @Mock
    private ScheduleMapper scheduleMapper;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    ScheduleServiceJPA scheduleServiceJPA;

    final int page = 0;
    final int size = 10;
    PageRequest pageRequest;
    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        pageRequest = PageRequest.of(page, size);
    }

    @Test
    void testGetSchedulesForMonth()
    {
        List<Schedule> schedules = List.of(
                Schedule.builder()
                        .date(java.sql.Date.valueOf("2021-01-10"))
                        .createdDate(java.sql.Date.valueOf("2021-01-01"))
                        .build(),
                Schedule.builder()
                        .date(java.sql.Date.valueOf("2021-01-02"))
                        .createdDate(java.sql.Date.valueOf("2021-01-01"))
                        .build()
        );
        when(scheduleRepository.findAllByDateBetween
                (Date.valueOf("2021-01-01"), Date.valueOf("2021-01-31"), pageRequest))
                .thenReturn(new PageImpl<>(schedules));
        when(scheduleMapper.toScheduleDTO(schedules.get(0))).thenReturn(ScheduleDTO.builder().date(Date.valueOf("2021-01-10")).build());
        when(scheduleMapper.toScheduleDTO(schedules.get(1))).thenReturn(ScheduleDTO.builder().date(Date.valueOf("2021-01-02")).build());
        Page<ScheduleDTO> scheduleDTOPage = scheduleServiceJPA.getSchedulesForMonth(1, 2021, page, size);
        assertEquals(2, scheduleDTOPage.getTotalElements());
        assertThat(scheduleDTOPage.getContent()).extracting(ScheduleDTO::getDate).containsExactlyInAnyOrder(
                Date.valueOf("2021-01-10"), Date.valueOf("2021-01-02")
        );
        verify(scheduleRepository).findAllByDateBetween(Date.valueOf("2021-01-01"), Date.valueOf("2021-01-31"), pageRequest);
        verify(scheduleMapper).toScheduleDTO(schedules.get(0));
        verify(scheduleMapper).toScheduleDTO(schedules.get(1));
    }
    @Test
    void testCreateSchedule()
    {
        UUID userId = UUID.randomUUID();
        ScheduleDTO scheduleDTO = ScheduleDTO.builder().build();
        AppUser user = AppUser.builder()
                .id(userId)
                .username("name")
                .password("psswd")
                .email("email")
                .lastLoginDate(Date.valueOf(LocalDate.now()))
                .isActive(true)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .lastModified(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Schedule schedule = new Schedule();
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(scheduleRepository.save(any(Schedule.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(scheduleMapper.toSchedule(scheduleDTO)).thenReturn(schedule);

        Schedule result = scheduleServiceJPA.createSchedule(scheduleDTO, userId);

        assertNotNull(result);
        assertEquals(user,result.getAppUser());
        assertTrue(user.getSchedules().contains(result));
        verify(appUserRepository).findById(userId);
        verify(scheduleRepository).save(schedule);
    }
}