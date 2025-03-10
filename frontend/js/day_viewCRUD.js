const scheduleTaskForm = document.getElementById('eventForm');
const currentDate = new Date(new URLSearchParams(window.location.search).get('date')).toISOString().split('T')[0];
scheduleTaskForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const eventTitle = document.getElementById('eventTitle').value;
    const eventDescription = document.getElementById('eventDescription').value;
    const eventStart = document.getElementById('eventStart').value;
    const eventEnd = document.getElementById('eventEnd').value;
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
    const response = await fetch('https://localhost:8443/schedules', {
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

document.addEventListener('DOMContentLoaded', async () => {
    const response = await fetch('https://localhost:8443/schedules/'+currentDate, {
        method: 'GET',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json',
        }
    });
    if (response.ok) {
        const data = await response.json();
        data.forEach(task => {
            calendar.addEvent({
                id: task.id,
                title: task.name + ': '+ task.description,
                start: task.startTime,
                end: task.endTime,
            });
        })
    } else {
        alert('Failed to fetch tasks');
    }
});