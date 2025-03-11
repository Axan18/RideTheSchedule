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
const scheduleChooser = document.getElementById('scheduleChooser');
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
    const scheduleSelect = document.querySelector('#users');
    sharers.forEach(schedule => {
        const scheduleElement = document.createElement('option');
        scheduleElement.value = schedule.id;
        scheduleElement.textContent = schedule.username;
        scheduleSelect.appendChild(scheduleElement);
    });
});