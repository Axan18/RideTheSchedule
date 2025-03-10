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