document.addEventListener('DOMContentLoaded', async () => {
    const response = await fetch('https://localhost:8443/auth/status', {
        method: 'GET',
        credentials: "include",
        headers: {
            'Content-Type': 'text/plain',
        }
    });
    const login = document.querySelector('.login');
    if (response.ok) { //logged in
        login.textContent = 'Logout';
        login.addEventListener('click', async (event) => {
            event.preventDefault();
            try {
                const response = await fetch('https://localhost:8443/auth/logout', {
                    method: 'POST',
                    credentials: "include",
                });

                if (response.ok) {
                    window.location.href = 'welcome.html';
                } else {
                    alert('Logout failed');
                }
            } catch (error) {
                console.error('Logout error:', error);
            }
        });
    }else {
        login.textContent = 'Login';
        login.href = 'https://localhost:8443/oauth2/authorization/google';
        login.onclick = null;
    }
});