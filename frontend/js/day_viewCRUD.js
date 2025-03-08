const scheduleTaskForm = document.getElementById('eventForm');
scheduleTaskForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const eventTitle = document.getElementById('eventTitle').value;
    const eventDescription = document.getElementById('eventDescription').value;
    const eventStart = document.getElementById('eventStart').value;
    const eventEnd = document.getElementById('eventEnd').value;
    const currentDate = new Date(new URLSearchParams(window.location.search).get('date')).toISOString().split('T')[0];
    let data = {
        name: eventTitle,
        description: eventDescription,
        startTime: eventStart,
        endTime: eventEnd,
        createdAt: new Date().toISOString().substring(0, 19),
        lastModified: new Date().toISOString().substring(0, 19)
    };
    const startTime = currentDate+'T'+data.startTime+':00';
    const endTime = currentDate+'T'+data.endTime+':00';
    data.startTime = startTime;
    data.endTime = endTime;

    console.log(data);
    const response = await fetch('https://localhost:8443/schedules/add_task', {
        method: 'POST',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    });
    if (response.ok) {
        alert('Task created');
        location.reload();
    } else {
        alert('Failed to create task');
    }
});
/*
public class ScheduleTaskDTO {
    private UUID id;
    @NotNull
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime lastModified;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
}
 */