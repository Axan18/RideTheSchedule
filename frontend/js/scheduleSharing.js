document.addEventListener('DOMContentLoaded', async function() {
    const response = await fetch('https://localhost:8443/users/friends/entities',
        {
            method: 'GET',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json',
            }
        });
    const friends = await response.json();
    const friendsList = document.querySelector('#friendsToShare');
    friends.forEach(friend => {
        const friendElement = document.createElement('option');
        friendElement.value = friend.id;
        friendElement.textContent = friend.username;
        friendsList.appendChild(friendElement);
    });
});
const shareForm = document.getElementById('shareForm');
shareForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const response = await fetch('https://localhost:8443/schedules/shared/'+currentDate+'?sharedWithId='+document.getElementById('friendsToShare').value,
        {
            method: 'POST',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json',
            }
        });
    if (response.ok) {
        alert('Schedule successfully shared');
        window.location.href = 'dayview.html?date='+currentDate;
    } else {
        alert('Failed to share schedule');
    }
});
document.addEventListener('DOMContentLoaded', async function() {
    const response = await fetch('https://localhost:8443/users/friends/sharers/'+currentDate,
        {
            method: 'GET',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json',
            }
        });
    const sharers = await response.json();
    const scheduleSelect = document.querySelector('#scheduleChooser');
    sharers.forEach(schedule => {
        const scheduleElement = document.createElement('option');
        scheduleElement.value = schedule.id;
        scheduleElement.textContent = schedule.username;
        scheduleSelect.appendChild(scheduleElement);
    });
});
const scheduleChooser = document.getElementById('scheduleChooser');
scheduleChooser.addEventListener('change', async (e) => {
    e.preventDefault();
    const chooseUserId = scheduleChooser.value;
    const username = scheduleChooser.options[scheduleChooser.selectedIndex].text;
    const response = await fetch('https://localhost:8443/schedules/shared/'+currentDate+'?sharedWithId='+chooseUserId,
        {
            method: 'GET',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json',
            }
        });
    if (response.ok) {
        const data = await response.json();
        calendar.removeAllEvents();
        data.forEach(task => {
            let custom_color = "rgba(31, 117, 163, 0.85)";
            if(task.name.includes(username))
                custom_color = "rgba(255, 3, 48, 0.65)";
            calendar.addEvent({
                id: task.id,
                title: task.name + ': '+ task.description,
                start: task.startTime,
                end: task.endTime,
                color: custom_color
            });
        })
    } else {
        alert('Failed to fetch tasks');
    }
});