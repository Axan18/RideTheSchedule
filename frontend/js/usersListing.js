const pageNumber = new URLSearchParams(window.location.search).get('page');
document.addEventListener('DOMContentLoaded', async () => {
    const response = await fetch(`https://localhost:8443/users?page=${pageNumber}&size=20&sort=username,asc`, {
        method: 'GET',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json',
        }
    });
    const friendsResponse = await fetch('https://localhost:8443/users/friends', {
        method: 'GET',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json',
        }
    });
    if (response.ok && friendsResponse.ok) {
        const data = await response.json();
        const friendsData = await friendsResponse.json();

        const container = document.getElementById('usersContainer');
        data.content.forEach(user => {
            const userDiv = document.createElement('div');
            const userText = document.createElement('p');
            userText.textContent = `${user.username} - Last seen: ${user.lastLoginDate} - Is active: ${user.isActive}`;
            userText.classList.add('col-8');
            userDiv.appendChild(userText);
            let friendshipStatus = null;
            const foundFriendship = friendsData.find(friend => {
                if (friend.user1Id === user.id) {
                    friendshipStatus = friend.status;
                    return true;
                }
                if (friend.user2Id === user.id) {
                    friendshipStatus = friend.status;
                    return true;
                }
                return false;
            });

            const isFriend = foundFriendship
                ? (foundFriendship.user1Id === user.id ? 2 : 1)
                : 0;

            if(isFriend>0 && friendshipStatus === 'ACCEPTED') {
                const unfriendButton = document.createElement('button');
                unfriendButton.classList.add('btn', 'btn-danger', 'm-2', 'col-2', 'offset-2');
                unfriendButton.textContent = 'Unfriend';
                unfriendButton.addEventListener('click', () => {deleteFriendship(user.id)});
                userDiv.appendChild(unfriendButton);
            }
            else if(isFriend === 1 && friendshipStatus === 'PENDING') {
                const cancelRequestButton = document.createElement('button');
                cancelRequestButton.classList.add('btn', 'btn-warning', 'm-2', 'col-2', 'offset-2');
                cancelRequestButton.textContent = 'Cancel request';
                cancelRequestButton.addEventListener('click', () => {cancelFriendshipRequest(user.id)});
                userDiv.appendChild(cancelRequestButton);
            }
            else if(isFriend === 2 && friendshipStatus === 'PENDING') {
                const cancelRequestButton = document.createElement('button');
                cancelRequestButton.classList.add('btn', 'btn-warning', 'm-2', 'col-2', 'offset-2');
                cancelRequestButton.textContent = 'Accept request';
                cancelRequestButton.addEventListener('click', () => {acceptFriendshipRequest(user.id)});
                userDiv.appendChild(cancelRequestButton);
            }
            else{
                const addFriendButton = document.createElement('button');
                addFriendButton.classList.add('btn', 'btn-success', 'm-2', 'col-2', 'offset-2');
                addFriendButton.textContent = 'Add friend';
                addFriendButton.addEventListener('click', () => {sendFriendshipRequest(user.id)});
                userDiv.appendChild(addFriendButton);
            }
            userDiv.classList.add('border', 'border-primary', 'rounded', 'p-2', 'm-2', 'row', 'align-items-center');
            container.appendChild(userDiv);
        })
        const pageSwitcher = document.createElement('div');
        pageSwitcher.classList.add('d-flex', 'justify-content-center');
        for (let i = 0; i < data.totalPages; i++) {
            const pageButton = document.createElement('button');
            pageButton.classList.add('btn', 'btn-primary', 'm-2');
            pageButton.textContent = i+1;
            pageButton.addEventListener('click', () => {
                window.location.href = `search.html?page=${i}`;
            });
            pageSwitcher.appendChild(pageButton);
        }
        container.appendChild(pageSwitcher);
    } else {
        alert('Failed to fetch users');
    }
});