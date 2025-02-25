document.querySelector('#register-submit').addEventListener("click", (event) => {
	event.preventDefault();

	const login = document.querySelector('#register-login').value
	const password = document.querySelector('#register-password').value

	console.log(JSON.stringify({
		password, login
	}));

	fetch("http://authentication-api:8080/register", {
		method: "POST",
		headers: {
			"Content-type": "application/json"
		},
		body: JSON.stringify({
			login, password
		}),
	}); // TODO docker compose pour que ca tourne
})