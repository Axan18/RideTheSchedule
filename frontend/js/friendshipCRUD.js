async function sendFriendshipRequest(friendId) {
    const response = await fetch(`https://localhost:8443/users/friends/add/${friendId}`, {
        method: 'POST',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json',
        }
    });
    if(response.ok){
        alert('Friendship request sent');
        location.reload();
    }
    else{
        alert('Failed to send friendship request');
    }
}
async function deleteFriendship(friendId) {
    const response = await fetch(`https://localhost:8443/users/friends/delete/${friendId}`, {
        method: 'DELETE',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json',
        }
    });
    if (response.ok) {
        alert('Friendship deleted');
        location.reload();
    } else {
        alert('Failed to delete friendship');
    }
}
async function cancelFriendshipRequest(friendId){
    const response = await fetch(`https://localhost:8443/users/friends/cancel/${friendId}`, {
        method: 'DELETE',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json',
        }
    });
    if(response.ok){
        alert('Friendship request cancelled');
        location.reload();
    }
    else{
        alert('Failed to cancel friendship request');
    }
}
async function acceptFriendshipRequest(friendId){
    const response = await fetch(`https://localhost:8443/users/friends/accept/${friendId}`, {
        method: 'PUT',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json',
        }
    });
    if(response.ok){
        alert('Friendship request accepted');
        location.reload();
    }
    else{
        alert('Failed to accept friendship request');
    }
}