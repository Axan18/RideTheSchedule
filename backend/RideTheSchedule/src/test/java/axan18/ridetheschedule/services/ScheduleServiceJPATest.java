package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.mappers.ScheduleMapper;
import axan18.ridetheschedule.models.ScheduleDTO;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceJPATest {
    @Mock
    private ScheduleMapper scheduleMapper;

    @Mock
    private ScheduleRepository scheduleRepository;

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
                Schedule.builder().date(java.sql.Date.valueOf("2021-01-10")).createdDate(java.sql.Date.valueOf("2021-01-01")).build(),
                Schedule.builder().date(java.sql.Date.valueOf("2021-01-02")).createdDate(java.sql.Date.valueOf("2021-01-01")).build()
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
}